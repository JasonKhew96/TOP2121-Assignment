(function() {
  "use strict"

  let dataCacheName = 'travelData-v01'
  let cacheName = 'travelPWA-alpha-01'
  let filesToCache = [
    '/',
    'index.html',
    'js/app.js',
    'js/jquery-3.3.1.slim.min.js',
    'js/popper.min.js',
    'js/bootstrap.min.js',
    'css/bootstrap.min.css'
  ]

  self.addEventListener('install', function(e) {
    console.log('[ServiceWorker] Install')
    e.waitUntil(
      caches.open(cacheName).then(function(cache) {
        console.log('[ServiceWorker] Caching app shell')
        return cache.addAll(filesToCache)
      })
    )
  })

  self.addEventListener('activate', function(e) {
    console.log('[ServiceWorker] Activate')
    e.waitUntil(
      caches.keys().then(function(keyList) {
        return Promise.all(keyList.map(function(key) {
          if (key !== cacheName && key !== dataCacheName) {
            console.log('[ServiceWorker] Removing old cache', key)
            return caches.delete(key)
          }
        }))
      })
    )
    return self.clients.claim()
  })

  self.addEventListener('fetch', function(e) {
    console.log('[Service Worker] Fetch', e.request.url)
    let getGuideUrl = '/getGuide'
    let getDetailsUrl = '/getDetails'
    if (e.request.url.indexOf(getGuideUrl) > -1 || e.request.url.indexOf(getDetailsUrl) > -1) {
      e.respondWith(
        caches.open(dataCacheName).then(function(cache) {
          return fetch(e.request).then(function(response) {
            cache.put(e.request.url, response.clone())
            return response
          })
        })
      )
    } else {
      e.respondWith(
        caches.match(e.request).then(function(response) {
          return response || fetch(e.request)
        })
      )
    }
  })
})()
