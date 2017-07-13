	var MyMar=null;
	function init(){
		var clubimage = document.getElementById('clubimage');
		var clubimage_list = document.getElementById('clubimage_list');
		var clubimage_t = document.getElementById('clubimage_t');
		var speed=30
		clubimage_t.innerHTML=clubimage_list.innerHTML 
		MyMar=setInterval(Marquee,speed) 
		clubimage.onmouseover=function() {clearInterval(MyMar)}
		clubimage.onmouseout=function() {MyMar=setInterval(Marquee,speed)}
	}

	function Marquee(){
		var clubimage = document.getElementById('clubimage');
		var clubimage_list = document.getElementById('clubimage_list');
		var clubimage_t = document.getElementById('clubimage_t');
			if(clubimage_t.offsetTop-clubimage.scrollTop<=0) 
				clubimage.scrollTop-=clubimage_list.offsetHeight 
			else{
				clubimage.scrollTop++
			}
	}