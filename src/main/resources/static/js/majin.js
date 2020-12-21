let token = $("meta[name='_csrf']").attr("content");
			let header = $("meta[name='_csrf_header']").attr("content");
	
			$(function() {
				$(document).ajaxSend(function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			});
			
			function G_search(search){

					$.ajax({
							url : "/search/recommendWord",
							type : "POST",
							data : {
								"search" : search
							},
							dataType : "text",
							success : function(retVal) {
								$("#recommendWord").val("");
								$("#recommendWord").replaceWith(retVal);
							},
							error : function() {
								alert("error");
							}
					});
						
					$.ajax({
						url : "/search/total",
						type : "POST",
						data : {
							"search" : search,
							"pageNo" : 1
						},
						dataType : "text",
						success : function(retVal) {
							$("#information").val("");
							/*$("#recommendWord").val("");
							$("#recommendList").remove();*/
							$("#information").replaceWith(retVal);
						},
						error : function() {
							alert("error");
						}
					});
					
					history.pushState(
							{pageNo : 1, 
							 search : search,
							 url : "/search/total"},
							 null, null);
			};
			
			$("#search").bind("keyup input", function(key) {
				let search = $("#search").val();
				if (search !== "" && key.keyCode === 13) {
					G_search(search);
				}
			});
			
			function G_recBtn(search){
				G_search(search);
			}
			
			$("#searchBtn").bind("click", function(){
				let search = $("#search").val();
				if(search !== ""){
					$.ajax({
						url : "/search/total",
						type : "POST",
						data : {
							"search" : search,
							"pageNo" : 1
						},
						dataType : "text",
						success : function(retVal) {
							$("#information").val("");
							$("#recommendWord").val("");
							$("#recommendList").remove();
							$("#information").replaceWith(retVal);
						},
						error : function() {
							alert("error");
						}
					});
				}
				
				history.pushState(
						{pageNo : 1, 
						 search : search,
						 url : "/search/total"},
						 null, null);
	
			});
	
			function horseDetail(hrNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/detail/horseDetail",
					type : "POST",
					data : {
						"hrNo" : hrNo,
						"meet" : meet
					},
					dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function() {
						alert("error");
					}
				});
				
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/detail/horseDetail",
						 meet : meet,
						 hrNo : hrNo},
						 null, null);
	
			}
	
			function trainerDetail(trNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/detail/trainerDetail",
					type : "POST",
					data : {
						"trNo" : trNo,
						"meet" : meet
					},
					dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function() {
						alert("error");
					}
				});
				
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/detail/trainerDetail",
						 meet : meet,
						 trNo : trNo},
						 null, null);
			}
	
			function jockeyDetail(jkNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/detail/jockeyDetail",
					type : "POST",
					data : {
						"jkNo" : jkNo,
						"meet" : meet
					},
					dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function() {
						alert("error");
					}
				});
				
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/detail/jockeyDetail",
						 meet : meet,
						 jkNo : jkNo},
						 null, null);
			}
			
			function yundo() {

				$.ajax({
					url : "/yundo/view",
					type : "POST",
					dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function() {
						alert("error");
					}
				});
				
				history.pushState(
						{url : "/yundo/view"},
						 null, null);
			}

			function hfirst(pageNo, search){
				horseSearchPaging(pageNo, search);
			}
			
			function hprevious(pageNo, search){
				horseSearchPaging(pageNo, search);
			}
			
			function hcurrent(pageNo, search){
				horseSearchPaging(pageNo, search);
			}
			
			function hnext(pageNo, search){
				horseSearchPaging(pageNo, search);
			}
			
			function hend(pageNo, search){
				horseSearchPaging(pageNo, search);
			}
			
			function tfirst(pageNo, search){
				trainerSearchPaging(pageNo, search);
			}
			
			function tprevious(pageNo, search){
				trainerSearchPaging(pageNo, search);
			}
			
			function tcurrent(pageNo, search){
				trainerSearchPaging(pageNo, search);
			}
			
			function tnext(pageNo, search){
				trainerSearchPaging(pageNo, search);
			}
			
			function tend(pageNo, search){
				trainerSearchPaging(pageNo, search);
			}
			
			function jfirst(pageNo, search){
				jockeySearchPaging(pageNo, search);
			}
			
			function jprevious(pageNo, search){
				jockeySearchPaging(pageNo, search);
			}
			
			function jcurrent(pageNo, search){
				jockeySearchPaging(pageNo, search);
			}
			
			function jnext(pageNo, search){
				jockeySearchPaging(pageNo, search);
			}
			
			function jend(pageNo, search){
				jockeySearchPaging(pageNo, search);
			}
			
			function horseSearchPaging(pageNo, search){
				$.ajax({
					url : "/search/horseSearchPaging",
					type : "POST",
					data : {
							"pageNo" : pageNo,
							"search" : search
							},
	                dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function(retVal) {
						alert("error");
					}
				});
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/horseSearchPaging",
						 fragment : "#horseP"},
						 null, null);	
			}
			
			function trainerSearchPaging(pageNo, search){
				$.ajax({
					url : "/search/trainerSearchPaging",
					type : "POST",
					data : {
							"pageNo" : pageNo,
							"search" : search
							},
	                dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function(retVal) {
						alert("error");
					}
				});
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/trainerSearchPaging",
						 fragment : "#trainerP"},
						 null, null);	
			}
			
			function jockeySearchPaging(pageNo, search){
				$.ajax({
					url : "/search/jockeySearchPaging",
					type : "POST",
					data : {
							"pageNo" : pageNo,
							"search" : search
							},
	                dataType : "text",
					success : function(retVal) {
						$("#information").val("");
						$("#information").replaceWith(retVal);
					},
					error : function(retVal) {
						alert("error");
					}
				});
				history.pushState(
						{pageNo : pageNo, 
						 search : search,
						 url : "/search/jockeySearchPaging",
						 fragment : "#jockeyP"},
						 null, null);	
			}
			
			$(window).on('popstate', function(event) {
		    	  let data = event.originalEvent.state;
				if (data == null){
					location.href="/";
				} else if (data.url === "/yundo/view"){	
					$.ajax({
						url : data.url,
						type : "POST",
						dataType : "text",
						success : function(retVal) {
							$("#information").val("");
							$("#information").replaceWith(retVal);
						},
						error : function() {
							alert("error");
						}
				});
		    	  } else if(data.url === "/guideList"){
		    	  	$.ajax({
							url : "/guideList",
							type : "POST",
							data : {
								"pageNo" : pageNo
			
							},
							dataType : "text",
							success : function(retVal) {
								$("#information").val("");
								$("#information").replaceWith(retVal);
							},
							error : function(retVal) {
								alert("error");
							}
						});
					
		    	  } else if(data.url === "/search/check"){
		    	  		$.ajax({
							url : "/search/check",
							type : "POST",
							data : data.form,
							dataType : "text",
							success : function(retVal) {
								$("#information").val("");
								$("#information").replaceWith(retVal);
								$("#startRcDate").val(data.startRcDate);
								$("#endRcDate").val(data.endRcDate);
								$("#hrName").val(data.hrName);
								$("#trName").val(data.trName);
								$("#jkName").val(data.jkName);
								$("#startRcDist").val(data.startRcDist);
								$("#endRcDate").val(data.endRcDate);
								$("#startWgBudam").val(data.startWgBudam);
								$("#endWgBudam").val(data.endWgBudam);
								$("#startRanks").val(data.startRanks);
								$("#endRanks").val(data.endRanks);
								$("#startChulNo").val(data.startChulNo);
								$("#endChulNo").val(data.endChulNo);
							},
							error : function(retVal) {
								alert("error");
							}
						});
		    	  } else if(data.url.substring(0,14) === "/search/detail"){
			    	  $.ajax({
							url : data.url,
							type : "POST",
							data : {
									"hrNo" : data.hrNo,
									"jkNo" : data.jkNo,
									"trNo" : data.trNo,
									"meet" : data.meet,
									},
			                dataType : "text",
							success : function(retVal) {
								$("#information").val("");
								$("#information").replaceWith(retVal);
							},
							error : function(retVal) {
								alert("error");
							}
						});
		    	  } else {
			    	  	$.ajax({
								url : "/search/recommendWord",
								type : "POST",
								data : {
									"search" : data.search,
								},
								dataType : "text",
								success : function(retVal) {
									$("#recommendWord").val("");
									$("#recommendWord").replaceWith(retVal);
								},
								error : function() {
									alert("error");
								}
						});
		    		  $.ajax({
							url : data.url,
							type : "POST",
							data : {
								"search" : data.search,
								"pageNo" : data.pageNo
							},
							dataType : "text",
							success : function(retVal) {
								$("#information").val("");
								/*$("#recommendWord").val("");
								$("#recommendList").remove();*/
								$("#information").replaceWith(retVal);
								
							},
							error : function() {
								alert("error");
							}
					});
		    	 }
		    }); 
		    
		    	function gfirst(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gprevious(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gcurrent(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gnext(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gend(pageNo) {
			GuideBoardPaging(pageNo);
		}
		
		function guideList() {
			GuideBoardPaging(1);
		}
		
		
		

		function GuideBoardPaging(pageNo) {
			$.ajax({
				url : "/guideList",
				type : "POST",
				data : {
					"pageNo" : pageNo

				},
				dataType : "text",
				success : function(retVal) {
					$("#information").val("");
					$("#information").replaceWith(retVal);
				},
				error : function(retVal) {
					alert("error");
				}
			});
			history.pushState({
				pageNo : pageNo,
				url : "/guideList",
				fragment : "#guide"
			}, null, null);
		};
		
		$("#multiSearchBtn").click(function(){
			$.ajax({
				url : "/search/mulSearch",
				type : "GET",
				dataType : "text",
				success : function(retVal) {
					$("#information").val("");
					$("#information").replaceWith(retVal);
				},
				error : function(retVal) {
					alert("error");
				}
			});
			history.pushState({				
				url : "/search/mulSearch",
				fragment : "#searchPage"
			}, null, null);
		});
		
		function multiSearchBtnStart(){
			multiSearchBtnSend(1);
		}
		
		function raceHfirst(pageNo) {
			multiSearchBtnSend(pageNo);
		}

		function raceHprevious(pageNo) {
			multiSearchBtnSend(pageNo);
		}

		function raceHcurrent(pageNo) {
			multiSearchBtnSend(pageNo);
		}

		function raceHnext(pageNo) {
			multiSearchBtnSend(pageNo);
		}

		function raceHend(pageNo) {
			multiSearchBtnSend(pageNo);
		}
		
		function multiSearchBtnSend(pageNo){
			let form = $("#searchForm").serialize();
			form += "&pageNo="+pageNo;
			
			let startRcDate = $("#startRcDate").val();
			let endRcDate =  $("#endRcDate").val();
			let hrName = $("#hrName").val();
			let trName = $("#trName").val();
			let jkName = $("#jkName").val();
			let startRcDist = $("#startRcDist").val();
			let endRcDist = $("#endRcDate").val();
			let startWgBudam = $("#startWgBudam").val();
			let endWgBudam = $("#endWgBudam").val();
			let startRanks = $("#startRanks").val();
			let endRanks = $("#endRanks").val();
			let startChulNo = $("#startChulNo").val();
			let endChulNo = $("#endChulNo").val();
			
			$.ajax({
				url : "/search/check",
				type : "POST",
				data : form,
				dataType : "text",
				success : function(retVal) {
					$("#information").val("");
					$("#information").replaceWith(retVal);
					$("#startRcDate").val(startRcDate);
					$("#endRcDate").val(endRcDate);
					$("#hrName").val(hrName);
					$("#trName").val(trName);
					$("#jkName").val(jkName);
					$("#startRcDist").val(startRcDist);
					$("#endRcDate").val(endRcDate);
					$("#startWgBudam").val(startWgBudam);
					$("#endWgBudam").val(endWgBudam);
					$("#startRanks").val(startRanks);
					$("#endRanks").val(endRanks);
					$("#startChulNo").val(startChulNo);
					$("#endChulNo").val(endChulNo);
				},
				error : function(retVal) {
					alert("error");
				}
			});
			history.pushState({				
				url : "/search/check",
				form : form,
				startRcDate : startRcDate,
				endRcDate : endRcDate,
				hrName : hrName,
				trName : trName,
				jkName : jkName,
				startRcDist : startRcDist,
				endRcDist : endRcDate,
				startWgBudam : startWgBudam,
				endWgBudam : endWgBudam,
				startRanks : startRanks,
				endRanks : endRanks,
				startChulNo : startChulNo,
				endChulNo : endChulNo,
				fragment : "#searchPage"
			}, null, null);
		};
		
			function gfirst(pageNo) {
		GuideBoardPaging(pageNo);
		}

		function gprevious(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gcurrent(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gnext(pageNo) {
			GuideBoardPaging(pageNo);
		}

		function gend(pageNo) {
			GuideBoardPaging(pageNo);
		}
		
		function guideList() {
			GuideBoardPaging(1);
		}
		

		function GuideBoardPaging(pageNo) {
			$.ajax({
				url : "/guideList",
				type : "GET",
				data : {
					"pageNo" : pageNo

				},
				dataType : "text",
				success : function(retVal) {
					$("#information").val("");
					$("#information").replaceWith(retVal);
				},
				error : function(retVal) {
					alert("error");
				}
			});
			history.pushState({
				pageNo : pageNo,
				url : "/guideList",
				fragment : "#guide"
			}, null, null);
		};
		
		function gsearch(){
			var tb = $("#gsearch").val();
			var tb2 = $("gsearchType").val();
			console.log("tb:" +tb);
			console.log("tb2:" +tb2);
			location.href="/guideSelect?search="+tb&tb2;
		};
		
		
		function gsfirst(pageNo, search) {
		GuideBoardSelect(pageNo, search);
		}

		function gsprevious(pageNo, search) {
			GuideBoardSelect(pageNo, search);
		}

		function gscurrent(pageNo, search) {
			GuideBoardSelect(pageNo, search);
		}

		function gsnext(pageNo, search) {
			GuideBoardSelect(pageNo, search);
		}

		function gsend(pageNo, search) {
			GuideBoardSelect(pageNo, search);
		}
		
		function gsuideList() {
			GuideBoardSelect(1);
		}
		

		function GuideBoardSelect(pageNo,search) {
			$.ajax({
				url : "/guideSelect",
				type : "GET",
				data : {
					"pageNo" : pageNo,
					"search" : search

				},
				dataType : "text",
				async: false,
				success : function(retVal) {
					$("#information").val("");
					$("#information").replaceWith(retVal);
				},
				error : function(retVal) {
					alert("error");
				}
			});
			history.pushState({
				pageNo : pageNo,
				search : search,
				url : "/guideSelect",
				fragment : "#guide"
			}, null, null);
		};
		