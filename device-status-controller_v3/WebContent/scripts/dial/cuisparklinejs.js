function cuiSparklineChart(config) {
	var width = config.width || 600;
	var height = config.height || 200;

	var sizePercent = config.sizePercent || '100%';
	var clipWidth = config.clipWidth || parseInt(width + width/15);
	var clipHeight = config.clipHeight || parseInt(height + height/3);

	var fontFamily = config.fontFamily || 'sans-serif';
	var border = config.border || '1px solid #ccc';

	var xAxisTickSize = config.xAxisTickSize || 7;
	var xAxisTickPadding = config.xAxisTickPadding || 4;
	var xAxisLabelColor = config.xAxisLabelColor || '#555';
	//var xAxisMargin = config.xAxisMargin || 0.5;
	var xAxisPathColor = config.xAxisPathColor || '#FFF';
	var xAxisLineColor = config.xAxisLineColor || '#777';
	var xAxisLineStrokeWidth = config.xAxisLineStrokeWidth || 0.5;
	var xAxisPathStrokeWidth = config.xAxisPathStrokeWidth || 0.5;
	var xAxisFontSize = config.xAxisFontSize || 10;

	var yAxisFormat = config.yAxisFormat || d3.format(',g');
	var yAxisTicks = config.yAxisTicks || 9;
	var yAxisTickSize = config.yAxisTickSize || 4;
	var yAxisTickPadding = config.yAxisTickPadding || 3;
	var yAxisLabelColor = config.yAxisLabelColor || '#777';
	var yAxisPathColor = config.yAxisPathColor || '#777';
	var yAxisLineColor = config.yAxisLineColor || '#777';
	var yAxisLineStrokeWidth = config.yAxisLineStrokeWidth || 0.5;
	var yAxisPathStrokeWidth = config.yAxisPathStrokeWidth || 0.5;
	var yAxisFontSize = config.yAxisFontSize || 10;

	var pathStrokeColor = config.pathStrokeColor || 'blue';
	var pathStrokeWidth = config.pathStrokeWidth || 1;

	var buttonLabels = config.buttonLabels || [ 'Pause', 'Resume' ];
	var buttonTextColor = config.buttonTextColor || '#000';
	var buttonColor = config.buttonColor || '#bbb';
	var buttonHoverColor = config.buttonHoverColor || '#ddd';
	var buttonWidth = config.buttonWidth || 38;
	var buttonHeight = config.buttonHeight || 20;
	var buttonX = config.buttonX || width - 40;
	var buttonY = config.buttonY || 0;

	var titleColor = config.titleColor || '#333';
	var titleText = config.titleText || 'Title';
	var titleX = config.titleX || 18;
	var titleY = config.titleY || 12;
	var titleFontSize = config.titleFontSize || 20;

	var subtitleColor = config.subtitleColor || '#222';
	var subtitleText = config.subtitleText || 'Subtitle';
	var subtitleX = config.subtitleX || 18;
	var subtitleY = config.subtitleY || 70;
	var subtitleFontSize = config.subtitleFontSize || 14;

	var yAxisText = config.yAxisText || 'value descriptor';
	var yAxisTextFontSize = config.yAxisTextFontSize || 10;
	var yAxisTextStyle = config.yAxisTextStyle || 'italic';
	var yAxisTextColor = config.yAxisTextColor || '#777';
	var yAxisTextX = config.yAxisTextX || -150;
	var yAxisTextY = config.yAxisTextY || 12;

	var focusTextFontSize = config.focusTextFontSize || 8;
	var focusCircleRadius = config.focusCircleRadius || 3.5;
	var focusCircleStrokeWidth = config.focusCircleStrokeWidth || 1;
	var focusCircleColor = config.focusCircleColor || 'red';
	var focusLineColor = config.focusLineColor || 'red';
	var focusLineDashArray = config.focusLineDashArray || '3 3';
	var focusLineWidth = config.focusLineWidth || 0.75;
	var focusTextFontStyle = config.focusTextFontStyle || 'italic';
	var focusTextFontColor = config.focusTextFontColor || '#111';
	var focusTextHoverYPos = config.focusTextHoverYPos || null;

	var backgroundColor = config.backgroundColor || '#ddd';
	var backgroundOpacity = config.backgroundOpacity || 0.3;

	var minY = config.minY || 0;
	var maxY = config.maxY || 100;
	//var minX = config.minX || 0;
	var maxX = config.maxX || 20;

  var tickDuration = config.tickDuration || 750;
  	
	// ........................
    
	var formatDate = d3.time.format('%I:%M:%S');
  var now = new Date(Date.now() - tickDuration);

	var data = d3.range(maxX).map(function() { return [0,0]; });

  //var lineInterpolation = 'linear' 'basis' 'monotone';
  var lineInterpolation = 'cardinal';
  
  var xDomain = [now - (maxX - 2) * tickDuration, now - tickDuration];
	var xScale = d3.time.scale()
    	.domain(xDomain)
    	.range([0, width]);

	var yDomain = [minY, maxY];
	var yScale = d3.scale.linear()
	    .domain(yDomain)
	    .range([height, 0]);

	var xAxis = d3.svg.axis()
	    .tickSize(xAxisTickSize,xAxisTickSize)
      .tickPadding(xAxisTickPadding)
			.scale(xScale)
			.orient('bottom');

	var yAxis = d3.svg.axis()
    	.ticks(yAxisTicks)
    	.tickSize(yAxisTickSize,yAxisTickSize)
    	.tickPadding(yAxisTickPadding)
    	.tickFormat(yAxisFormat)
			.scale(yScale)
			.orient('left');

	var line = d3.svg.line()
    	.interpolate(lineInterpolation)
    	.x(function(d, i) { return xScale(now - (maxX - 1 - i) * tickDuration); })
    	.y(function(d, i) { return yScale(d[1]); });

	var path;
	var gXAxis;
	var gYAxis;
	var buttonText;
	var autoPause = false;
	var manualPause = false;

	// ----------------

  function chart(selection) {
    selection.each(function(dataValue) {
	    // Select the svg element, if it exists...
    	var svg = d3.select(this).selectAll('svg').data([data]);

    	// ...else create initial 'skeletal' svg (magically bound to selection)...
    	var gEnter = svg.enter().append('svg').append('g');
	      	
			var gTitle = gEnter.append('g');

    	var gPath = gEnter.append('g').attr('clip-path', 'url(#clip)');
    	path = gPath.append('path').attr('class', 'line');
    	path.style('fill', 'none')
		    .style('stroke-width', pathStrokeWidth)
		    .style('stroke', pathStrokeColor);

    	gXAxis = gEnter.append('g');
    	gYAxis = gEnter.append('g');

			// general attributes
			svg.style('text-anchor', 'start')
				.style('border', border)
    		.style('font-family', fontFamily);

    	// clip path
			svg.append('defs').append('clipPath')
			    .attr('id', 'clip')
			  .append('rect')
			    .attr('width', width)
			    .attr('height', height);

		   // outer dimensions.
			svg.attr('width', sizePercent)
  			.attr('height', sizePercent)
  			.attr('viewBox','0 0 '+ clipWidth + ' ' + clipHeight)
  			.attr('preserveAspectRatio','none');

	    // inner dimensions.
      var g = svg.select('g')
		    .attr('transform', 'translate(' + width/20 + ',' + height/15 +')');

      // cross-hairs focus tracking
      var focus = g.append('g').style('display', 'none');
      focus.append('circle')
        .attr('id', 'focusCircle')
        .attr('r', focusCircleRadius)
		  	.attr('fill', 'none')
		  	.style('stroke-width', focusCircleStrokeWidth + 'px')
		  	.style('stroke', focusCircleColor);
      focus.append('line')
        .attr('id', 'focusLineX')
		  	.attr('fill', 'none')
		  	.style('stroke-dasharray', focusLineDashArray)
		  	.style('stroke-width', focusLineWidth + 'px')
		  	.style('stroke', focusLineColor);
      focus.append('line')
        .attr('id', 'focusLineY')
		  	.attr('fill', 'none')
		  	.style('stroke-dasharray', focusLineDashArray)
		  	.style('stroke-width', focusLineWidth + 'px')
		  	.style('stroke', focusLineColor);
  		focus.append('text')
        .attr('id', 'focusTextY')
				.attr('xml:space', 'preserve')
        .style('text-anchor','end') 
        .style('font-size', focusTextFontSize + 'px')
		   	.style('font-style', focusTextFontStyle)
		  	.style('fill', focusTextFontColor);
  		focus.append('text')
        .attr('id', 'focusTextX')
        .attr('xml:space', 'preserve')
        .style('text-anchor', 'start')
		  	.style('font-size', focusTextFontSize + 'px')
		   	.style('font-style', focusTextFontStyle)
		  	.style('fill', focusTextFontColor);

      var bisectDate = d3.bisector(function(d) { return d[0]; }).left;

			var frozenData = null;
      g.append('rect')
	    	.attr('fill', backgroundColor)
	    	.style('stroke', 'none')
	    	.style('opacity', backgroundOpacity)
	    	.attr('pointer-events', 'all')
        .attr('width', width)
        .attr('height', height)
        .on('mouseover', function() { 
        	autoPause = true;
        	focus.style('display', null); 
        	frozenData = data.slice(0);
        })
        .on('mouseout', function() { 
        	autoPause = false;
        	focus.style('display', 'none'); 
        })
        .on('mousemove', function() { 
          var mouse = d3.mouse(this);
          var x0 = xScale.invert(mouse[0]);

        	var lo = 1;
          var i = bisectDate(frozenData, x0, lo); 

          var d0 = frozenData[i - 1];
          var d1 = frozenData[i];
          var d = x0 - d0[0] > d1[0] - x0 ? d1 : d0;

          var x = xScale(d[0]);
          var y = yScale(d[1]);

          focus.select('#focusCircle')
            .attr('cx', x)
            .attr('cy', y);
          focus.select('#focusLineX')
            .attr('x1', x).attr('y1', yScale(yDomain[0]))
            .attr('x2', x).attr('y2', yScale(yDomain[1]));
          focus.select('#focusLineY')
            .attr('x1', xScale(xDomain[0])).attr('y1', y)
            .attr('x2', xScale(xDomain[1])).attr('y2', y);
         	focus.select('#focusTextX')
            .attr('x', x)
            .attr('y', focusTextHoverYPos || y - 5)
         		.text( '  ' + formatDate(d[0]) );
         	focus.select('#focusTextY')
            .attr('x', x)
            .attr('y', focusTextHoverYPos || y - 5)
         		.text( yAxisFormat(d[1]) + '   ');
        });
			// ...focus


		  // button
			var bg = g.append('g');
			var button = bg.append('rect')
	      .attr('fill', buttonColor)
	      .attr('rx', 3)
	      .attr('ry', 3)
	      .attr('x', buttonX)
	      .attr('y', buttonY)
	      .attr('width', buttonWidth)
	      .attr('height', buttonHeight)
	      .on('mouseover', function(d,i){
	        d3.select(this).style('cursor', 'pointer');
	        d3.select(this).attr('fill', buttonHoverColor);
	      })
	      .on('mouseout', function(d,i){
	        d3.select(this).attr('fill', buttonColor);
	      });
			buttonText = bg.append('text')
	      .attr('fill', buttonTextColor)
	      .attr('x', buttonX + (buttonWidth/2) ) 
	      .attr('y', buttonY + (buttonHeight/5))
	      .attr('dy', buttonHeight/2.5 +'px')
	      .style('font-size',  buttonHeight/2.5 + 'px')
	      .style('text-anchor', 'middle')
	      .text(buttonLabels[0])
	      .on('mouseover', function(d,i){
	        d3.select(this).style('cursor', 'pointer');
	        button.attr('fill', buttonHoverColor);
	      })
	      .on('mouseout', function(d,i){
	        button.attr('fill', buttonColor);
	      });
			button.on('click', pauseButonClicked);
			buttonText.on('click', pauseButonClicked);


			// title
			gTitle.append('text')
		    .attr('x', titleX)
		   	.attr('y', titleY)
		    .style('text-anchor', 'start')
		    .style('font-size', titleFontSize + 'px')
		    .style('fill', titleColor)
		    .text(titleText);
			// subtitle
			gTitle.append('text')
		    .attr('x', subtitleX)
		   	.attr('y', subtitleY)
		    .style('text-anchor', 'start')
		    .style('font-size', subtitleFontSize + 'px')
		    .style('fill', subtitleColor)
		    .text(subtitleText);


	    // x-axis.
      gXAxis.attr('fill', xAxisLabelColor)
  			.style('font-size', xAxisFontSize +'px')
		    //.attr('transform', 'translate(0,' + yScale(0) + ')')
		    .attr('transform', 'translate(0,' + yScale(minY) + ')')
    		.call(xAxis);
		  gXAxis.selectAll('path')
      	.attr('fill', 'none')
      	.style('stroke-width', xAxisPathStrokeWidth)
      	.style('stroke', xAxisPathColor);
		  gXAxis.selectAll('line')
      	.style('stroke-width', xAxisLineStrokeWidth)
      	.style('stroke', xAxisLineColor);
			

		  // y-axis.
			gYAxis.attr('fill', yAxisLabelColor)
		    .style('font-size', yAxisFontSize +'px')
        .call(yAxis);
	    gYAxis.selectAll('path')
	    	.attr('fill', 'none')
	    	.style('stroke-width', yAxisPathStrokeWidth)
	    	.style('stroke', yAxisPathColor);
	    gYAxis.selectAll('line')
	    	.style('stroke-width', yAxisLineStrokeWidth)
	    	.style('stroke', yAxisLineColor);
	    gYAxis.append('text')
	      .style('font-size', yAxisTextFontSize +'px')
	      .style('font-style', yAxisTextStyle)
	      .style('fill', yAxisTextColor)
	      .attr('transform', 'rotate(-90)')
	      .attr('x',yAxisTextX)
	      .attr('y',yAxisTextY)
	      .text(yAxisText);

			// update line with passed in data value
			chart.tick(dataValue);
  	});
	}

	function pauseButonClicked() {
    var isPause = (buttonText.text() === buttonLabels[0]);

    manualPause = isPause;

    buttonText.text(isPause ? buttonLabels[1] : buttonLabels[0]);
	}

	chart.tick = function(value) {
		// push a new data point onto the back
	  now = new Date();
		data.push([now, value]);
		
	  if (! autoPause && ! manualPause) {
			// update domain(s)	  	
	  	xDomain = [now - (maxX - 2) * tickDuration, now - tickDuration];
	  	xScale.domain(xDomain);

			// update line with new data
			path.attr('d', line).attr('transform', null);
		    
	    // shift the axis
	    // NB magic number below...
	    var transitionDuration = tickDuration * 0.85 ;
			gXAxis.transition()
	  		.duration(transitionDuration)
	  		.ease('linear')
	  		.call(xAxis);
	    gXAxis.selectAll('path')
      	.attr('fill', 'none')
      	.style('stroke-width', xAxisPathStrokeWidth)
      	.style('stroke', xAxisPathColor);
	    gXAxis.selectAll('line')
      	.style('stroke-width', xAxisLineStrokeWidth)
      	.style('stroke', xAxisLineColor);

	  	// shift the line
	    path.transition()
	    	.duration(transitionDuration)
	    	.ease('linear')
	    	.attr('transform', 'translate(' + xScale(now - (maxX - 1) * tickDuration) + ')');
		}
		
		// pop the oldest data point off the front
		data.shift();

		return chart;
	};

	/*
	chart.width = function(_) {
	    if (!arguments.length) return width;
	    width = _;
		return chart;
	};
	chart.height = function(_) {
	    if (!arguments.length) return height;
	    height = _;
	    return chart;
	};
	*/
	chart.randomData = function() {
		//return ( Math.round(Math.random() * config.maxY) );
		return ( Math.floor(Math.random() * config.maxY) );
	};

	return chart;
}
