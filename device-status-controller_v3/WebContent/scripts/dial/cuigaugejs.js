// ---------------------------------------
// The widget factory...
var cuiGauge = function(configuration) {
	var that = {};

	var config = {
		selector: '',
		dataFile: null,
		playIntervalMs: 4000,
		drillDownURL: null,

		showDataControls: false,

		size		: 200,
		sizePercent	: 100.0,
		clipWidth	: 200,
		clipHeight	: 110,
		viewBoxX: null,
		viewBoxY: null,

		ringInset	: 20,
		ringWidth	: 20,
		
		borderWidth: 5,

		fontFamily: 'sans-serif',

		pointerWidth			: 10,
		pointerTailLength		: 10,
		pointerHeadLengthPercent: 0.9,
		pointerColor		: 'blue',
		pointerStroke		: 'white',

		valueTextOn 	: true,
		valueTextColor	: 'black',
		valueTextStyle	: '',
		valueFormat: d3.format(',g'),
		valueSuffix: '',
		valueFontSize: 35,
		valueX: 0,
		valueY: 0,

		minValue	: 0,
		maxValue	: 10,
		
		minAngle	: -90,
		maxAngle	: 90,
		minAngleWarn	: 70,
		maxAngleWarn	: 80,
		minAngleError	: 80,
		maxAngleError	: 90,
		
		transitionMs: 750,
		
		majorTicks: 5,
		tickX: 0,
		tickY: 0,
		tickMajorMinorFilter: 2,
		tickMajorColor: 'black',
		tickMinorColor: 'black',
		tickMajorWidth: 2,
		tickMinorWidth: 1,
		tickYMajorStart: 0,
		tickYMinorStart: 0,
		tickYMajorEnd: 22,
		tickYMinorEnd: 16,
		tickXMajorStart: 0,
		tickXMinorStart: 0,
		tickXMajorEnd: 0,
		tickXMinorEnd: 0,

		labelFormat	: d3.format(',g'),
		labelSuffix: '',
		labelStyle	: '',
		labelColor	: 'black',
		labelFontSize: 20,
		labelX: 0,
		labelY: 0,
		labelFilter: 1,

		title		: '',
		titleColor	: 'black',
		titleStyle	: '',
		titleFontSize: 35,
		titleX: 0,
		titleY: 0,

		subtitle		: '',
		subtitleColor	: 'black',
		subtitleStyle	: '',
		subTitleFontSize: 25,
		subtitleX: 0,
		subtitleY: 0,

		arcColorFn	: d3.interpolateHsl(d3.rgb('red'), d3.rgb('green')),
		arcBorderColorFn	: d3.interpolateHsl(d3.rgb('black'), d3.rgb('black')),
		arcWarnColorFn	: d3.interpolateHsl(d3.rgb('yellow'), d3.rgb('gold')),
		arcErrorColorFn	: d3.interpolateHsl(d3.rgb('pink'), d3.rgb('red')),
	};

	var r;
	var range;
	var rangeWarn;
	var rangeError;
	var svg;
	var arc;
	var arcBorder;
	var arcWarn;
	var arcError;
	var scale;
	var ticks;
	var tickData;
	var pointer;
	var pointerHeadLength;
	var valueText;
	var dataControls;
	var viewBoxX;
	var viewBoxY;

	function deg2rad(deg) {
		return deg * Math.PI / 180;
	}
	
	/*
	function newAngle(d) {
		var ratio = scale(d);
		var angle = config.minAngle + (ratio * range);
		return angle;
	}
	*/

	function centerTranslation() {
		return 'translate(' + r + ',' + r + ')';
	}

	function randomData() {
		return ( Math.round(Math.random() * config.maxValue) );
	}
	that.randomData = randomData;

  function valueTween(transition, oldValue, newValue) {
      transition.attrTween('d', function(d) {
          var interpolate = d3.interpolate(oldValue, newValue);
          return function(t) {
            var interimValue = interpolate(t);
          	valueText.text(config.valueFormat(Math.round(interimValue)) + config.valueSuffix);
          };
      });
  }
	
	function configure(configuration) {
		var prop;
		for ( prop in configuration ) {
			config[prop] = configuration[prop];
		}
		
		viewBoxX = (config.viewBoxX !== null) ? config.viewBoxX : (config.borderWidth) * -1;
		viewBoxY = (config.viewBoxY !== null) ? config.viewBoxY : (config.borderWidth) * -1;

		range = config.maxAngle - config.minAngle;
		r = (config.size / 2) - config.borderWidth;
		pointerHeadLength = Math.round(r * config.pointerHeadLengthPercent);

		scale = d3.scale.linear()
			.range([0,1])
			.domain([config.minValue, config.maxValue]);
			
		ticks = scale.ticks(config.majorTicks);
		tickData = d3.range(config.majorTicks).map(function() {return 1/config.majorTicks;});

		arcBorder = d3.svg.arc()
	    .innerRadius(r)
	    .outerRadius(r + config.borderWidth)
			.startAngle(function(d, i) {
				return -180;
			})
			.endAngle(function(d, i) {
				return 180;
			});


		arc = d3.svg.arc()
			.innerRadius(r - config.ringWidth - config.ringInset)
			.outerRadius(r - config.ringInset)
			.startAngle(function(d, i) {
				var ratio = d * i;
				return deg2rad(config.minAngle + (ratio * range));
			})
			.endAngle(function(d, i) {
				var ratio = d * (i+1);
				return deg2rad(config.minAngle + (ratio * range));
			});

		rangeWarn = config.maxAngleWarn - config.minAngleWarn;
		if (rangeWarn > 0) {
			arcWarn = d3.svg.arc()
				.innerRadius(r - config.ringWidth - config.ringInset)
				.outerRadius(r - config.ringInset)
				.startAngle(function(d, i) {
					var ratio = d * i;
					return deg2rad(config.minAngleWarn + (ratio * rangeWarn));
				})
				.endAngle(function(d, i) {
					var ratio = d * (i+1);
					return deg2rad(config.minAngleWarn + (ratio * rangeWarn));
				});
		}
		
		rangeError = config.maxAngleError - config.minAngleError;
		if (rangeError > 0) {
			arcError = d3.svg.arc()
				.innerRadius(r - config.ringWidth - config.ringInset)
				.outerRadius(r - config.ringInset)
				.startAngle(function(d, i) {
					var ratio = d * i;
					return deg2rad(config.minAngleError + (ratio * rangeError));
				})
				.endAngle(function(d, i) {
					var ratio = d * (i+1);
					return deg2rad(config.minAngleError + (ratio * rangeError));
				});
		}

		if (config.showDataControls) {
	    /*
	    dataControls = data_controls({
				dataFile: config.dataFile,
				randomDataFn: randomData,
				updateFn: update,
				playIntervalMs: config.playIntervalMs,
				containerClassName: config.selector,
				controlWidth: config.sizePercent+'%',
	    });
			*/
	  }

	}
	that.configure = configure;
	

	function render(newValue) {
		var gaugeContainer = d3.select(config.selector);

		svg = gaugeContainer.append('svg')
			.style('text-anchor', 'middle')
			.style('font-family', config.fontFamily)
			.attr('width', config.sizePercent +'%')
			.attr('height', config.sizePercent +'%')
			.attr('viewBox', viewBoxY + ' ' + viewBoxX + ' ' + parseInt(config.clipWidth) + ' ' + parseInt(config.clipHeight) )
			.attr('preserveAspectRatio','xMidYMid');


		var centerTx = centerTranslation();

		var arcs = svg.append('g').attr('transform', centerTx);
		arcs.selectAll('path')
				.data(tickData)
			.enter().append('path')
				.attr('fill', function(d, i) {
					return config.arcColorFn(d * i);
				})
				.transition()
      	.delay(function(d, i) { return i * 40; })
				.attr('d', arc);
		
		var baseArcs = arcs;

		if (arcWarn) {
			var arcsWarn = svg.append('g').attr('transform', centerTx);
			arcsWarn.selectAll('path')
					.data(tickData)
				.enter().append('path')
					.attr('fill', function(d, i) {
						return config.arcWarnColorFn(d * i);
					})
					.transition()
	      	.delay(function(d, i) { return i * 40; })
					.attr('d', arcWarn);
			baseArcs = arcsWarn;
		}

		if (arcError) {
			var arcsError = svg.append('g').attr('transform', centerTx);
			arcsError.selectAll('path')
					.data(tickData)
				.enter().append('path')
					.attr('fill', function(d, i) {
						return config.arcErrorColorFn(d * i);
					})
					.transition()
	      	.delay(function(d, i) { return i * 40; })
					.attr('d', arcError);
			baseArcs = arcsError;

		var bordeArcs = svg.append('g').attr('transform', centerTx);
		bordeArcs.selectAll('path')
				.data(tickData)
			.enter().append('path')
				.attr('fill', function(d, i) {
					return config.arcBorderColorFn(d * i);
				})
				.transition()
      	.delay(function(d, i) { return i * 40; })
				.attr('d', arcBorder);
		}


		baseArcs.append('text')
      .attr('transform', 'translate(' + config.titleX + ',' + config.titleY + ')')
			.attr('fill', config.titleColor)
   		.style('font-size', config.titleFontSize + 'px')
   		.style('font-style', config.titleStyle)
			.text(config.title);

		baseArcs.append('text')
      .attr('transform', 'translate(' + config.subtitleX + ',' + config.subtitleY + ')')
			.attr('fill', config.subtitleColor)
   		.style('font-style', config.subtitleStyle)
  		.style('font-size', config.subtitleFontSize + 'px')
  		.style('z-index',2)
			.text(config.subtitle);

		
		var lg = svg.append('g').attr('transform', centerTx);

		lg.selectAll('line')
				.data(ticks)
			.enter().append('line')
				.attr('transform', function(d) {
					var ratio = scale(d);
					var newAngle = config.minAngle + (ratio * range);
					return 'rotate(' + newAngle +') translate(' + config.tickX + ',' + config.tickY +')';
				})
				.transition()
      			.delay(function(d, i) { return i * 30; })
				.attr('stroke', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickMajorColor : config.tickMinorColor);
				})
				.attr('stroke-width', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickMajorWidth : config.tickMinorWidth);
				})
				.attr('x1', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickXMajorStart : config.tickXMinorStart);
				})
		    .attr('x2', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickXMajorStart : config.tickXMinorStart);
				})
		    .attr('y1', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickYMajorStart : config.tickYMinorStart);
				})
		    .attr('y2', function(d,i) {
					return ((i % config.tickMajorMinorFilter === 0) ? config.tickYMajorEnd : config.tickYMinorEnd);
				});

		lg.selectAll('text')
				.data(ticks)
			.enter().append('text')
				.attr('transform', function(d) {
					var ratio = scale(d);
					var newAngle = config.minAngle + (ratio * range);
					return 'rotate(' + newAngle +') translate(' + config.labelX + ',' + config.labelY +')';
				})
				.transition()
      			.delay(function(d, i) { return i * 30; })
				.attr('fill', config.labelColor)
	      		.style('font-size', function(d,i) {
	      			return ((i % config.labelFilter === 0) ? config.labelFontSize : 0) + 'px';
	      		})
	      		.style('font-style', config.labelStyle)
				.text(config.labelFormat); 



		var pointerData = [ [config.pointerWidth / 2, 0],
						[0, -pointerHeadLength],
						[-(config.pointerWidth / 2), 0],
						[0, config.pointerTailLength],
						[config.pointerWidth / 2, 0] ];

		var pointerLine = d3.svg.line().interpolate('monotone');
		
		var pg = svg.append('g').data([pointerData])
			.attr('fill', config.pointerColor)
			.attr('stroke', config.pointerStroke)
			.attr('stroke-width', config.pointerWidth/7)
			.attr('transform', centerTx);
				
		pointer = pg.append('path')
			.attr('d', pointerLine)
			.attr('transform', 'rotate(' + config.minAngle +')');


		if (config.valueTextOn) {
			valueText = pg.append('a')
				.attr('target','_blank')
				.attr('xlink:href', config.drillDownURL)
				.append('text')
				.attr('fill', config.valueTextColor)
				.attr('stroke-width', '0')
	      		.style('font-size', config.valueFontSize + 'px')
	      		.style('font-style', config.valueTextStyle)
				.attr('transform', 'translate(' + parseInt(config.valueX) + ',' + parseInt(config.valueY) + ')');
		}

		if (dataControls) {
	    dataControls.render();			
		}
	}
	that.render = render;


	var oldValue = 0;
	function update(newValue, newConfiguration) {
		if (newConfiguration !== undefined) {
			configure(newConfiguration);
		}
		
		var ratio = scale(newValue);
		var newAngle = config.minAngle + (ratio * range);
		
		pointer.transition()
			.duration(config.transitionMs)
			.ease('elastic')
			.attr('transform', 'rotate(' + newAngle +')');

		if (config.valueTextOn) {
			valueText.transition()
				.duration(config.transitionMs)
				.call(valueTween, oldValue, newValue);
			oldValue = newValue;
		}
	}
	that.update = update;


	// Instantiate...
	configure(configuration);
	render();
	if (dataControls) {
		dataControls.start();		
	}

	return that;
};
// ---------------------------------------
