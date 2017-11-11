let coap = require('coap')

let baseActorFormat = {
	"class-name": "hello"
}

let actorPOST = {
	host: 'localhost',
	pathname: 'actor',
	method: 'GET',
	confirmable: true,
}

let req = coap.request(actorPOST)
req.setOption('Location-Path', ['location', 'hello'])
req.setOption('ETag', 'class-name')
req.write(JSON.stringify(baseActorFormat))

req.on('response', function(res) {
	console.log(res.payload + "")
	res.on('end', function() {
		process.exit(0)
	})
})

req.end()
