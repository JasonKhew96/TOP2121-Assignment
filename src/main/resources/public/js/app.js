(function() {
  "use strict"

  let app = {
    container: document.querySelector(".main"),
    cardTemplate: document.querySelector(".cardTemplate"),
    detailsTemplate: document.querySelector(".details-template"),
    visibleCards: {}
  }

  app.updateCard = function(data) {
    for (let i = 0; i < data.items.length; i++) {
      let item = data.items[i]
      let image = item.img
      let title = item.title
      let intro = item.intro
      let id = item.id
      let card = app.visibleCards[item.id]
      if (!card) {
        card = app.cardTemplate.cloneNode(true)
        card.classList.remove("cardTemplate")
        card.removeAttribute("hidden")
        app.container.appendChild(card)
        app.visibleCards[item.id] = card
      }
      card.querySelector(".card-img-top").src = image
      card.querySelector(".card-title").textContent = title
      card.querySelector(".card-text").textContent = intro
      card.querySelector(".details-btn").onmouseup = function() {
        app.getDetails(id)
      }
    }
  }

  app.getGuide = function(id) {
    let url = "./getGuide"
    if ("caches" in window) {
      caches.match(url).then(function(response) {
        if (response) {
          response.json().then(function(json) {
            app.updateCard(json)
          })
        }
      })
    }
    window.fetch(url).then(function(response) {
      if (response) {
        response.json().then(function(json) {
          app.updateCard(json)
        })
      }
    })
  }

  app.updateDetails = function(data, id) {
    let title = data.title
    let intro = data.intro
    let content = data.content
    let detailsTemplate = app.detailsTemplate
    detailsTemplate.querySelector(".details-title").textContent = title
    detailsTemplate.querySelector(".details-intro").textContent = intro
    detailsTemplate.querySelector(".details-content").textContent = content
    detailsTemplate.removeAttribute("hidden")
    document.querySelector(".card-main").hidden = true
  }

  app.getDetails = function(id) {
    let url = "./getDetails/" + id
    if ("caches" in window) {
      caches.match(url).then(function(response) {
        if (response) {
          response.json().then(function(json) {
            window.history.pushState({
              data: json,
              id: id
            }, null, "?id=" + id)
            document.title = json.title
            app.updateDetails(json, id)
          })
        }
      })
    }
    window.fetch(url).then(function(response) {
      if (response) {
        response.json().then(function(json) {
          if (!history.state) {
            window.history.pushState({
              data: json,
              id: id
            }, null, "?id=" + id)
          }
          document.title = json.title
          app.updateDetails(json, id)
        })
      }
    })
  }

  window.addEventListener('popstate', function(e) {
    if (e.state) {
      app.updateDetails(e.state.data, e.state.id)
    } else {
      document.title = "Travel Guide System"
      app.detailsTemplate.hidden = true
      document.querySelector(".card-main").hidden = false
    }
  })

  // let initialList = {
  //   items: [{
  //       img: "https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1447946383/amsterdam-DGLP1115.jpg?itok=KLnSmZUR&timestamp=1447947376",
  //       title: "Place 1",
  //       intro: "Place 1 introduction blah blah",
  //       id: "place_1"
  //     },
  //     {
  //       img: "https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1449679670/atlanta-DGLP1115.jpg?itok=OXjU82LK&timestamp=1449679812",
  //       title: "Place 2",
  //       intro: "Place 2 introduction blah blah",
  //       id: "place_2"
  //     },
  //     {
  //       img: "https://cdn-image.travelandleisure.com/sites/default/files/styles/tlp_295x187_one_fourth/public/1463075833/auckland-DGLP0516.jpg?itok=wgrDPduS&timestamp=1463075946",
  //       title: "Place 3",
  //       intro: "Place 3 introduction blah blah",
  //       id: "place_3"
  //     }
  //   ]
  // }
  // let initialDetails = {
  //   title: "Testing title",
  //   intro: "Intro Testing",
  //   content: "Testing aflkdsaj ljsadlkfjsa fjads fjksah dklhs ajlhjfklsahfjskahf lsadjkl hjksafhkasldhf"
  // }
  // app.updateCard(initialList)
  // app.updateDetails(initialDetails)

  if ((window.location.pathname == "/" || window.location.pathname == "/index.html") && window.location.search.substr(1).length == 0) {
    app.getGuide()
  } else {
    let params = window.location.search.substr(1)
    let id = params.split("id=")[1]
    app.getDetails(id)
  }

  if ('serviceWorker' in navigator) {
    navigator.serviceWorker
      .register('./service-worker.js')
      .then(function() {
        console.log('Service Worker Registered');
      });
  }
})()
