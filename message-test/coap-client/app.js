let coap = require('coap')

let baseActorFormat = {
	foo: "bar"
}

let actorPOST = {
	host: 'localhost',
	pathname: 'actor',
	method: 'PUT',
	confirmable: true,
}

let req = coap.request(actorPOST)
req.setOption('Location-Path', ['childActorSystem', 'orc1'])
req.setOption('Location-Query', ['org.dsngroup.orcar.sample.SourceAndPrint'])
req.write(JSON.stringify(baseActorFormat))
req.on('response', function(res) {
	console.log(res.payload + "")
	res.on('end', function() {
		process.exit(0)
	})
})

req.end()
