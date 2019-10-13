var express = require('express');
var scrape = require('html-metadata');
const fs = require('fs');
var app = express();

app.set('view engine', 'ejs');

app.get('/add', function(req, res) {
	var url = req.query.url;
	var yes = req.query.yes;
	scrape(url).then(function(metadata){
		console.log(metadata);
		var mdata;
		if(typeof metadata !== 'undefined') {
			if(typeof metadata.openGraph !== 'undefined') {
				metadata.openGraph['waste'] = typeof yes == 'undefined';
				mdata = metadata.openGraph;
			} else if(typeof metadata.general !== 'undefined') {
				metadata.general['waste'] = typeof yes == 'undefined';
				mdata = metadata.general;
			}
		} 
		
		if(typeof mdata !== 'undefined') {
			console.log(`Metadata extracted: ${mdata}\n`);
			fs.writeFile("C:/users/mihir/documents/server/append.txt", JSON.stringify(mdata), function(err) {
				if(err) {
					return console.log(err);
				}

				console.log("The file was saved!");
			}); 
		} else {
			console.log('Metadata not extracted');
		}
	});
	res.render('home');
});

app.listen(8080, function() {
	console.log('Server started!');
});
