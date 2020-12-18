let token = $("meta[name='_csrf']").attr("content");
			let header = $("meta[name='_csrf_header']").attr("content");
	
			$(function() {
				$(document).ajaxSend(function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			});
	
			$("#search").bind("keyup input", function(key) {
				let search = $("#search").val();
				if (key.keyCode !== 13) {
					/* $.ajax({
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
					}); */
				} else if (search !== "") {
					$.ajax({
						url : "/search",
						type : "POST",
						async: false,
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
					
					history.pushState(
							{pageNo : 1, 
							 search : search,
							 url : "/search"},
							 null, null);
				};
			});
			
			$("#searchBtn").bind("click", function(){
				let search = $("#search").val();
				if(search !== ""){
				console.log("searchBtn");
					$.ajax({
						url : "/search",
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
						 url : "/search"},
						 null, null);
	
			});
	
			function horseDetail(hrNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/horseDetail",
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
						 url : "/search/horseDetail",
						 meet : meet,
						 hrNo : hrNo},
						 null, null);
	
			}
	
			function trainerDetail(trNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/trainerDetail",
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
						 url : "/search/trainerDetail",
						 meet : meet,
						 trNo : trNo},
						 null, null);
			}
	
			function jockeyDetail(jkNo, meet, search, pageNo) {

				$.ajax({
					url : "/search/jockeyDetail",
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
						 url : "/search/jockeyDetail",
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
				}else if(data.url !== "/search"){
			    	  $.ajax({
							url : data.url,
							type : "POST",
							data : {
									"pageNo" : data.pageNo,
									"search" : data.search
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
		    	  } else if (data.url === "/yundo/view"){
		   				yundo();
		    	  } else {
		    		  $.ajax({
							url : "/search",
							type : "POST",
							data : {
								"search" : data.search,
								"pageNo" : data.pageNo
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
		    }); 
		    
		function nfirst(pageNo) {
			NoticeBoardPaging(pageNo);
		}

		function nprevious(pageNo) {
			NoticeBoardPaging(pageNo);
		}

		function ncurrent(pageNo) {
			NoticeBoardPaging(pageNo);
		}

		function nnext(pageNo) {
			NoticeBoardPaging(pageNo);
		}

		function nend(pageNo) {
			NoticeBoardPaging(pageNo);
		}
		
		function noticeList() {
			NoticeBoardPaging(1);
		}
		

		function NoticeBoardPaging(pageNo) {
			console.log("test");
			$.ajax({
				url : "/noticeList",
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
				url : "/noticeList",
				fragment : "#notice"
			}, null, null);
		};
		
		
		function nsearch(){
			var tt = $("#nsearch").val();
			console.log("tt:" +tt);
			location.href="/noticeSelect?search="+tt;
		};
		
		
		function nsfirst(pageNo, search) {
		NoticeBoardSelect(pageNo, search);
		}

		function nsprevious(pageNo, search) {
			NoticeBoardSelect(pageNo, search);
		}

		function nscurrent(pageNo, search) {
			NoticeBoardSelect(pageNo, search);
		}

		function nsnext(pageNo, search) {
			NoticeBoardSelect(pageNo, search);
		}

		function nsend(pageNo, search) {
			NoticeBoardSelect(pageNo, search);
		}
		
		function nsuideList() {
			NoticeBoardSelect(1);
		}
		

		function NoticeBoardSelect(pageNo,search) {
			console.log("test");
			$.ajax({
				url : "/noticeSelect",
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
				url : "/noticeSelect",
				fragment : "#notice"
			}, null, null);
		};
				 
				 