// import axios from 'axios'
//
// const service = axios.create({
//   timeout: 5000, // 请求超时时间
//   withCredentials: true
// })
// // request拦截器
// service.interceptors.request.use(config => {
//   return config
// }, error => {
//   // Do something with request error
//   console.log(error) // for debug
//   Promise.reject(error)
// })
// export default service
import axios from 'axios'
import qs from 'qs'
// 创建axios实例
const service = axios.create({
  baseURL: process.env.API_ROOT, // api的base_url
  timeout: 10000, // 请求超时时
  withCredentials: true
})

service.interceptors.request.use(config => {
  const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
  config.headers['Authorization'] = accessToken
  return config
}, error => {
  Promise.reject(error)
})

// request拦截器
service.interceptors.request.use(
  config => {
    // x-www-form-urlencoded
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
    config.data = qs.stringify({
      ...config.data
    })
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    return response.data
  },
  error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
  }
)

export default service
