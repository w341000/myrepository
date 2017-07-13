	function comment_support(commentid){
		if(commentid != getCookie("currentCommentID")){
			var form = document.forms["supportOropposeForm"];
			form.method.value = "support";
			form.productId.value = commentid;			
			var objspcount = document.getElementById('spcount_'+ commentid);			
			form.submit();
			objspcount.innerText = parseInt(objspcount.innerText) +1;
			document.getElementById("commentiframe").src="about:blank"
			setCookie("currentCommentID", commentid, 10*60);
		}else{
			alert("您已经为该贴投过票");
		}
	}

    function comment_oppose(commentid){
		if(commentid != getCookie("currentCommentID")){
			var form = document.forms["supportOropposeForm"];
			form.method.value = "oppose";
			form.productId.value = commentid;
			var objopcount = document.getElementById('opcount_'+ commentid); 			
			form.submit();
			objopcount.innerText = parseInt(objopcount.innerText) +1;
			document.getElementById("commentiframe").src="about:blank"
			setCookie("currentCommentID", commentid, 10*60);
		}else{
			alert("您已经为该贴投过票");
		}
	}