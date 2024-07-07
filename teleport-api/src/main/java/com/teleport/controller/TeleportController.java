package com.teleport.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import com.teleport.config.R;
import com.teleport.entity.FileInfo;
import com.teleport.entity.RequestDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.datatransfer.DataFlavor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping
public class TeleportController {

    @Value("${download-path}")
    private String downloadPath;

    @Value("${upload-path}")
    private String uploadPath;

    @GetMapping({"list"})
    public R list() {
        File file = new File(this.downloadPath);
        FileUtil.mkdir(file);
        File[] files = file.listFiles(File::isFile);
        if (ArrayUtil.isEmpty((Object[])files)) {
            return R.success(CollUtil.newArrayList());
        }
        List<FileInfo> list = Arrays.stream(files).map(item -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(item.getName());
            fileInfo.setSize(DataSizeUtil.format(FileUtil.size(item)));
            return fileInfo;
        }).collect(Collectors.toList());
        return R.success(list);
    }

    @GetMapping({"download"})
    public void download(String fileName, HttpServletResponse response) {
        try(BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(new File(this.downloadPath, fileName).toPath()));
            ServletOutputStream servletOutputStream = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.addHeader(Header.CONTENT_LENGTH.getValue(), String.valueOf(FileUtil.size(new File(this.downloadPath, fileName))));
            IoUtil.copy(inputStream, servletOutputStream);
        } catch (Exception e) {
            log.error("下载异常", e);
        }
    }

    @SuppressWarnings("all")
    @PostMapping({"upload"})
    public R upload(MultipartFile file) {
        try {
            File uploadPathFile = new File(this.uploadPath);
            FileUtil.mkdir(uploadPathFile);
            Assert.notBlank(file.getOriginalFilename(), "文件名不能为空");
            File target = new File(uploadPathFile, file.getOriginalFilename());
            if(target.exists()){
                return R.error("当前文件已存在");
            }
            file.transferTo(target);
            return R.success();
        } catch (IOException e) {
            log.error("上传异常", e);
            return R.error(e.getMessage());
        }
    }

    @GetMapping("uploadCheck")
    public R uploadCheck(String fileName){
        File target = new File(this.uploadPath, fileName);
        if(target.exists()){
            return R.error("当前文件已存在");
        }
        return R.success();
    }

    @PostMapping({"addClipboard"})
    public R addClipboard(@RequestBody RequestDTO dto) {
        if (StrUtil.isBlank(dto.getValue())) {
            return R.error("内容不能为空");
        }
        ClipboardUtil.setStr(dto.getValue());
        return R.success();
    }

    @GetMapping({"getClipboard"})
    public R getClipboard() {
        try {
            Object o = ClipboardUtil.get(DataFlavor.stringFlavor);
            if (Objects.isNull(o)){
                return R.error("剪切板内容为空");
            }
            String content = o.toString();
            if (StrUtil.isBlank(content)) {
                return R.error("剪切板内容为空");
            }
            return R.success(content);
        } catch (Exception e) {
            log.error("获取剪切板异常", e);
            return R.error("获取剪切板异常：" + e.getMessage());
        }
    }
}
