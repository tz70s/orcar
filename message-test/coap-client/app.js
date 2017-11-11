let coap = require('coap')

let baseActorFormat = {}

let actorPOST = {
	host: 'localhost',
	pathname: 'actor',
	method: 'GET',
	confirmable: true,
}

let req = coap.request(actorPOST)
req.setOption('Location-Path', ['childActorSystem', 'grandActorSystem'])

req.on('response', function(res) {
	console.log(res.payload + "")
	res.on('end', function() {
		process.exit(0)
	})
})

req.end()
