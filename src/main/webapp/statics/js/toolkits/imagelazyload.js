/**
 * image lazyload tool.
 * Use as following:
 * 1. init as tlr_imgLazyLoad.initImgLazyLoadInfo("#prodDetailCotentDiv img[original]"); 
 * 2. execute lazyloading as tlr_imgLazyLoad.imgLazyLoad();
 */
var tlr_imgLazyLoad = {
	lazyLoadedImgArr:[],
	lazyLoadedImgSize:6,//每次延迟加载图片的数量
	lazyLoadedCheckTime:1000,//检测延迟加载图片时间
	//初始化图片延迟加载信息
	/**
	 * divExp  "#prodDetailCotentDiv img[original]"
	 */
	initImgLazyLoadInfo:function(divExp){
		jQuery(divExp).each(function(){
			var _this = jQuery(this);
			tlr_imgLazyLoad.lazyLoadedImgArr.push(_this);
		});	
	},
	//延迟加载图片信息
	imgLazyLoad:function(){
		if(tlr_imgLazyLoad.lazyLoadedImgArr==null || tlr_imgLazyLoad.lazyLoadedImgArr.length<=0){
			return false;
		}
		var curHeight=jQuery(document).scrollTop()+jQuery(window).height();
		var imgHeigth=tlr_imgLazyLoad.lazyLoadedImgArr[0].offset().top;
		if(curHeight<imgHeigth){
			setTimeout(tlr_imgLazyLoad.imgLazyLoad,tlr_imgLazyLoad.lazyLoadedCheckTime);
//			console.log("未达到延迟加载位置:["+curHeight+","+imgHeigth);
			return true;
		}
		
		var toLoadImgArr = tlr_imgLazyLoad.lazyLoadedImgArr.splice(0, tlr_imgLazyLoad.lazyLoadedImgSize);
		
		for(var i=0;i<toLoadImgArr.length;i++){
			var _this = toLoadImgArr[i];
			var val = _this.attr('original');
			_this.removeAttr('original');
			_this.attr("src",val);
//			console.log("加载img="+val+";idnex="+i);
		}
//		console.log("剩余size="+tlr_imgLazyLoad.lazyLoadedImgArr.length);
		setTimeout(tlr_imgLazyLoad.imgLazyLoad,tlr_imgLazyLoad.lazyLoadedCheckTime);
		return true;
	}
};