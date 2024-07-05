import axios from 'axios'
import { showDialog } from 'vant'

const api = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  headers: {
    common: {
      'Content-Type': 'application/json'
    }
  }
})

const request = async (config) => {
  try {
    const response = await api(config)
    if (response.data.code === 200) {
      return response.data
    } else {
      showDialog({
        title: '异常提示',
        message: response.data.message
      }).then(() => {})
      return Promise.reject(response.data.message)
    }
  } catch (err) {
    showDialog({
      title: '异常提示',
      message: '接口异常!'
    }).then(() => {})
    return Promise.reject(new Error('接口异常!'))
  }
}

export default request
