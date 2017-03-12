/**
 * Created by Administrator on 2016/11/2.
 */
$(function(){
    var winheight=$(window).height();
    var winwidth=$(window).width();

    var loginheight=$('#logindiv').height();
    var loginwidth=$('#logindiv').width();


    $('#logindiv').css('position','absolute');
    $('#logindiv').css('top',(winheight/2-loginheight/2)+'px');
    $('#logindiv').css('left',(winwidth/2-loginwidth/2)+'px');
    //用户帐号输入失去焦点后
    $('#account').bind('blur',function(){
    	var account =$(this).val();
    	if(account){
    		var regxp = /^[a-z0-9\u2E80-\u9FFF_-]{3,16}$/;
    		if(regxp.test(account)){
    			//	发送请求验证用户名
    			$.ajax({
    				type:'post',
    				url:'user/UserServlet',
    				data:{'account':account,"method":'checkAccount'},
    				success:function(result){
    					re = eval("("+result+")");
    					if(re['statusId']==1){
    						$('#account').next('.msg').css('color','green');
    					}else{
    						$('#account').next('.msg').css('color','red');

    					}
    					$('#account').next('.msg').html(re['msg']);
    				},
    				error:function(){
    				$('#account').next('.msg').html("服务端异常").css('color','red');	
    				}
    			});
    		}else{
    			$('#account').next('.msg').html('请检查用户名格式').css('color','red');
    		}//if(regxp.test(account))
    	}//if(account)
    });
});