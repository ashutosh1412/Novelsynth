function init() {
	new Ajax.Request('http://localhost:8080/surveydata', {
		method: 'get',
		asynchronous: false,
		onSuccess: function(response) {
			if (response.status === 200) {
				data = response.responseJSON;
				createView(data);
			}
		}
	});
}
function createView(data) {
	let table = createTable(data);
	let graph = createGraph(data);
	let root1 = document.getElementById('root1');
	root1.appendChild(table);
}

function createGraph(data) {

	let mDVsIncl = {
		x: data['MD*m'],
		y: data['Incl*deg'],
		mode: 'lines+markers',
		type: 'scatter'
	};

	let mDVsAzim = {
		x: data['MD*m'],
		y: data['Azim*deg'],
		mode: 'lines+markers',
		type: 'scatter'
	}

	let data1 = [mDVsIncl];
	let data2 = [mDVsAzim];

	let layout1 = {
		title: 'MD vs Incl'
	};
	let layout2 = {
		title: 'MD vs Azim'
	};

	Plotly.newPlot('root21', data1,layout1);
	Plotly.newPlot('root22', data2,layout2);

}

function createTable(data) {

	let table = document.createElement("TABLE");
	table.setAttribute("class", "table table-striped table-responsive");
	table.setAttribute("style", "height:100%;padding: 5%;");
	let thead = document.createElement("THEAD");
	let headers = [];
	let units = [];
	for (let x in data) {
		let val = x.split("*")[0];
		let th = document.createElement("TH");
		th.setAttribute("scope", "col");
		th.innerHTML = val;
		if (headers.indexOf(val) === -1) {
			headers.push(val);
			console.log(val);
			units.push(x.split('*')[1]);
		}
		thead.appendChild(th);
	}
	table.appendChild(thead);
	thead = document.createElement("THEAD");
	for (let j = 0; j < units.length; j++) {
		let th = document.createElement("TH");
		th.setAttribute("scope", "col");
		th.innerHTML = units[j];
		thead.appendChild(th);
	}
	table.appendChild(thead);
	let tbody = document.createElement("TBODY");
	let count = 0;
	for (let i = 0; i < data[headers[0] + '*' + units[0]].length; i++) {
		let tr = document.createElement("TR");
		for (let j = 0; j < headers.length; j++) {
			let td = document.createElement("TD");
			td.setAttribute("scope", "row");
			td.innerHTML = data[headers[j] + '*' + units[j]][i];
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	table.appendChild(tbody);
	return table;
}