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
	createGraph(data);
}

function createGraph(data) {
	let viewData = [];
	for (let file in data) {
		let dateIndex = 1;
		let MDIndex = 0;
		let MWIndex = 0;
		let headers = data[file]['headers'];
		for (let i = 2; i  < headers.length;i++)  {
			if (headers[i] === 'Measured Depth'){ 
			MDIndex = i;
		}
			else if (headers[i] === 'Mud Weight'){ 
			MWIndex = i;
		}
	}
	let graphData = {
		x: data[file]['data'][MDIndex],
		y: data[file]['data'][MWIndex],
		z: data[file]['data'][1],
		mode: 'lines+markers',
		type: 'scatter3d',
		name: file
	};
	viewData.push(graphData);
}
let layout = {
	title: 'Assigment 2',
	xaxis: {
		title: 'Measured Depth',
		side: 'top'
	},
	yaxis: {
		title: 'Mud Weight'
	},
	zaxis: {
		title: 'Time'
	}
};
let config = { responsive: true }
Plotly.newPlot('root', viewData, layout, config);
}
