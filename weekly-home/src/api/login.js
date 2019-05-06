import request from '@/utils/request'

const base = process.env.API_ROOT
export function login (username, password) {
  return request({
    url: base + '/weeklyusercenter/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function get (url, data) {
  return request({
    url: url,
    data: data
  })
}
export function post (url, data) {
  return request({
    url: base + url,
    method: 'post',
    data: data
  })
}
export function getInfo (token) {
  return request({
    url: '/bbsusercenter/frontuser/info',
    method: 'post',
    data: {token: token}
  })
}
export function logout (token) {
  return request({
    url: '/weeklyusercenter/frontUser/logout',
    data: {token: token},
    method: 'post'
  })
}
