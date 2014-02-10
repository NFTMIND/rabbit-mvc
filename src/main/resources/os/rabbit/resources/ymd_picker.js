// JavaScript Document
// JavaScript Document
$(function() {

	var daysInMonth = function(iMonth, iYear) {
		return 32 - new Date(iYear, iMonth, 32).getDate();
	};

	var padding = function(v) {
		return (v < 10) ? ("0" + v) : v;
	};

	$(".ymd_picker").each(
					function() {

						var now = new Date()
						var thisyear = 0;
						if (typeof (window.innerWidth) == 'number') {
							thisyear = now.getYear() + 1900;
						} else {
							thisyear = now.getYear();
						}

						var target = $(this).hide();
						var id = target.attr("id");

						var yearLabel = (target.attr("y_l")) ? target.attr("y_l") : "/";
						var monthLabel = (target.attr("m_l")) ? target.attr("m_l") : "/";
						var dayLabel = (target.attr("d_l")) ? target.attr("d_l") : "";
						var yearPickerLabel = (target.attr("y_p_l")) ? target.attr("y_p_l") : "Year";
						var monthPickerLabel = (target.attr("m_p_l")) ? target.attr("m_p_l") : "Month";
						var dayPickerLabel = (target.attr("d_p_l")) ? target.attr("d_p_l") : "Day";
						var seperate = (target.attr("seperate")) ? target.attr("seperate") : "/";
						var nextFocus = (target.attr("nf")) ? false : true;
						var birthdayPicker = (target.attr("birthday")) ?true:false;

						var container = $("<span></span>").addClass("birthday");
						var year = $("<input>").attr("readonly", "readonly").addClass("year");
						// year.attr("id", id+"_year");
						var month = $("<input>").attr("readonly", "readonly").addClass("month");
						// month.attr("id", id+"_month");
						var day = $("<input>").attr("readonly", "readonly").addClass("day");
						// day.attr("id", id+"_day");
						var picker = $("<div>").addClass("birthday-picker").hide();

						if (target.val() != "" && target.val().split(seperate).length == 3) {
							var vals = target.val().split(seperate);
							year.val(vals[0]);
							month.val(vals[1]);
							day.val(vals[2]);
						}
						var setVal = function() {
							var val = Array();
							if (year.val() != "" && month.val() != "" && day.val() != "")
								target.val(year.val() + seperate + month.val() + seperate + day.val());
							// alert(target.val());
						};
						var setPos = function() {
							var offset = year.offset();
							picker.css("left", offset.left);
							picker.css("top", offset.top + year.get(0).offsetHeight);
						};
						
						var lastTen = function(v) {
							picker.empty();
							
							var hundred = $("<ol>").addClass("year");
							
							var start = v - 10;
							var end = v + 1;

							for (i = start; i <= end; i += 1) {
								var tenCon = $("<li>");
								var ten = $("<ol>");
								var one;
								if (i == start)
									one = $("<li>◄</li>");
								else if (i == end)
									one = $("<li>►</li>");
								else
									one = $("<li>" + (i) + "</li>");
								if (i == parseInt(year.val()))
									one.addClass("checked");
								(function() {
									var val = i;
									one.appendTo(ten);
									if (val == start) {
										one.click(function() {
											lastTen(val);
										});
									} else if (val == end) {
										one.click(function() {
											nextTen(val);
										});
									} else {
										one.click(function() {
											year.val(val);
											setVal();
											picker.hide();
											yp_over();
											if (nextFocus)
												month.focus();

										});
									}
								})();

								ten.appendTo(tenCon);
								tenCon.appendTo(hundred);
							}
							$("<div>" + yearPickerLabel + "</div>").addClass("head").appendTo(picker);
							hundred.appendTo(picker);
							$(picker).show();
							setPos();
						};
						var nextTen = function(v) {
							picker.empty();
							
							var hundred = $("<ol>").addClass("year");
							
							var start = v - 1;
							var end = v + 10;

							for (i = start; i <= end; i += 1) {
								var tenCon = $("<li>");
								var ten = $("<ol>");
								var one;
								if (i == start)
									one = $("<li>◄</li>");
								else if (i == end)
									one = $("<li>►</li>");
								else
									one = $("<li>" + (i) + "</li>");
								if (i == parseInt(year.val()))
									one.addClass("checked");
								(function() {
									var val = i;
									one.appendTo(ten);
									if (val == start) {
										one.click(function() {
											lastTen(val);
										});
									} else if (val == end) {
										one.click(function() {
											nextTen(val);
										});
									} else {
										one.click(function() {
											year.val(val);
											setVal();
											picker.hide();
											yp_over();
											if (nextFocus)
												month.focus();

										});
									}
								})();

								ten.appendTo(tenCon);
								tenCon.appendTo(hundred);
							}
							$("<div>" + yearPickerLabel + "</div>").addClass("head").appendTo(picker);
							hundred.appendTo(picker);
							$(picker).show();
							setPos();
						};

						year.appendTo(container);
						$("<span>" + yearLabel + "</span>").addClass("seperate").appendTo(container);
						month.appendTo(container);
						$("<span>" + monthLabel + "</span>").addClass("seperate").appendTo(container);
						day.appendTo(container);
						$("<span>" + dayLabel + "</span>").addClass("seperate").appendTo(container);
						container.insertAfter(target);
						picker.appendTo($("body")[0]);
						if(!birthdayPicker){
						year.focus(function() {
							picker.empty();
							
							var hundred = $("<ol>").addClass("year");
							var ny = (year.val())?parseInt(year.val()):thisyear;
							var start = ny - 6;
							var end = ny + 5;

							for (i = start; i <= end; i += 1) {
								var tenCon = $("<li>");
								var ten = $("<ol>");
								var one;
								if (i == start)
									one = $("<li>◄</li>");
								else if (i == end)
									one = $("<li>►</li>");
								else
									one = $("<li>" + (i) + "</li>");
								if (i == parseInt(year.val()))
									one.addClass("checked");
								(function() {
									var val = i;
									one.appendTo(ten);
									if (val == start) {
										one.click(function() {
											lastTen(val);
										});
									} else if (val == end) {
										one.click(function() {
											nextTen(val);
										});
									} else {
										one.click(function() {
											year.val(val);
											setVal();
											picker.hide();
											yp_over();
											if (nextFocus)
												month.focus();

										});
									}
								})();

								ten.appendTo(tenCon);
								tenCon.appendTo(hundred);
							}
							$("<div>" + yearPickerLabel + "</div>").addClass("head").appendTo(picker);
							hundred.appendTo(picker);
							$(picker).show();
							setPos();
						});
						}else{
						 year.focus(function() {
										picker.empty();
										
										var hundred = $("<ol>").addClass("year");
										var sYear = thisyear - 110;
										for (i = sYear; i <= thisyear; i += 10) {
											var tenCon = $("<li>");
											var ten = $("<ol>");
											for (j = 0; j < 10; j += 1) {
												var one = $("<li>" + (i + j)+ "</li>");
												if (i + j == parseInt(year.val()))
													one.addClass("checked");
												(function() {
													var val = i + j;
													one.appendTo(ten);
													one.click(function() {
														year.val(val);
														setVal();
														picker.hide();
														yp_over();
														if (nextFocus)
															month.focus();
													});
												})();
											}
											ten.appendTo(tenCon);
											tenCon.appendTo(hundred);
										}
										$("<div>" + yearPickerLabel + "</div>").addClass("head").appendTo(picker);
										hundred.appendTo(picker);
										$(picker).show();
										setPos();
									});
						}
						month.focus(function() {
							picker.empty();
							var all = $("<ol>").addClass("month");
							for (i = 1; i <= 12; i += 1) {
								var one = $("<li>" + i + "</li>");
								if (i % 4 == 1)
									one.addClass("one");
								if (i == parseInt(month.val()))
									one.addClass("checked");
								(function() {
									var val = i;
									one.appendTo(all);
									one.click(function() {
										month.val(padding(val));
										setVal();
										picker.hide();
										mp_over();
										if (nextFocus)
											day.focus();

									});
								})();
							}
							$("<div>" + monthPickerLabel + "</div>").addClass(
									"head").appendTo(picker);
							all.appendTo(picker);
							// alert(all.html());
							picker.show();
							setPos();
						});

						day.focus(function() {
							picker.empty();
							var all = $("<ol>").addClass("day");
							for (i = 1; i <= daysInMonth(month.val() - 1, year
									.val()); i += 1) {
								var one = $("<li>" + i + "</li>");
								if (i % 7 == 1)
									one.addClass("one");
								if (i == parseInt(day.val()))
									one.addClass("checked");
								(function() {
									var val = i;
									one.appendTo(all);
									one.click(function() {
										day.val(padding(val));
										setVal();
										picker.hide();

										//gogo(id);
										dp_over();
									});
								})();
							}
							$("<div>" + dayPickerLabel + "</div>").addClass(
									"head").appendTo(picker);
							all.appendTo(picker);
							$(picker).show();
							setPos();
						});
					});

});