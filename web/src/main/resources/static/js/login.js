$(function(){
	// login Form Valid
	var loginFormValid = $("#login").validate({
        focusInvalid : true,
        submitHandler : function(form) {
			$.post("login", $("#login").serialize(), function(data, status) {
			    if(data.status === 0){
			        window.location.href = _ctx + 'index';
			    }else{
				    toastr.error(data.msg);
                }
			});
		}
	});
});