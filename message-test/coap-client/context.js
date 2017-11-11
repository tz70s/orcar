let coap = require('coap')

let contextGET = {
	host: 'localhost',
	pathname: 'context',
	method: 'PUT',
	confirmable: true,
}

let req = coap.request(contextGET)
let context = {
	'node-name': '10',
	'runtime-thread-pool-size': 4
}

req.write(JSON.stringify(context))
req.on('response', function(res) {
	console.log(res.payload + "")
	res.on('end', function() {
		process.exit(0)
	})
})

req.end()
