<%@ page language="java" pageEncoding="UTF-8"%>

<html>
  <head>
   <title>编辑用户</title>
   <LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
   <script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath }/script/validate.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/My97DatePicker/WdatePicker.js"></script>
   <script language="javascript" src="${pageContext.request.contextPath }/script/showText.js"></script>
   <script language="javascript" src="${pageContext.request.contextPath }/script/limitedTextarea.js"></script>
   <script language="javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.js"></script>
   <Script language="javascript">
    
	function check_null(){
	
		var theForm=document.Form1;
	    
	  
	    if(Trim(theForm.userName.value)=="")
		{
			alert("用户姓名不能为空");
			theForm.userName.focus();
			return false;
		}
		if(theForm.sexID.value=="")
		{
			alert("请选择性别");
			theForm.sexID.focus();
			return false;
		}
	    if(theForm.jctID.value=="")
		{
			alert("请选择所属单位");
			theForm.jctID.focus();
			return false;
		}
	    if(Trim(theForm.onDutyDate.value)=="")
		{
			alert("入职时间不能为空");
			theForm.onDutyDate.focus();
			return false;
		}
	    if(theForm.postID.value=="")
		{
			alert("请选择职位");
			theForm.postID.focus();
			return false;
		}
        if(theForm.logonPwd.value!=theForm.passwordconfirm.value){
		
		  alert("两次输入密码不一致，请重新输入");
		  return;
		}
		if(checkNull(theForm.contactTel)){
         if(!checkPhone(theForm.contactTel.value))
		  {
			alert("请输入正确的电话号码");
			theForm.contactTel.focus();
			return false;
		  }
		}
		
	    if(checkNull(theForm.mobile)){
         if(!checkMobilPhone(theForm.mobile.value))
		  {
			alert("请输入正确的手机号码");
			theForm.mobile.focus();
			return false;
		  }
		}
		
	   if(checkNull(theForm.email))	{
         if(!checkEmail(theForm.email.value))
		 {
			alert("请输入正确的EMail");
			theForm.email.focus();
			return false;
		 }
	   }
		
	   if(theForm.remark.value.length>250){
     
        	alert("备注字符长度不能超过250");
			theForm.remark.focus();
			return false; 
       }
	   //如果是否在职选择是，则入职时间必须要填写，如果是否在职选择否，则离职日期必须要填写
	   var isDutySelect = document.getElementById("isDuty");
	   //选择[是]
	   if(isDutySelect.options[0].selected){
		   if(theForm.onDutyDate.value==""){
			   alert("该用户属于在职人员，请填写入职时间");
			   theForm.onDutyDate.focus();
			   return false; 
		   }
	   }
	   //选择[否]
	   else{
		   if(theForm.offDutyDate.value==""){
			   alert("该用户属于离职人员，请填写离职时间");
			   theForm.offDutyDate.focus();
			   return false; 
		   }
	   }
	   
	   document.Form1.action="editUser.do";
	   document.Form1.submit();
	  	
	}
	function checkTextAreaLen(){
  		var remark = new Bs_LimitedTextarea('remark', 250); 
  		remark.infolineCssStyle = "font-family:arial; font-size:11px; color:gray;";
  		remark.draw();	
    }
    window.onload=function(){
		checkTextAreaLen();
    }
    
    /**如果选择离职时间，则【是否在职】默认选择"否"，如果没有选择离职时间，则【是否在职】默认选择"是"*/
    function checkIsDuty(o){   
 	   var offDutyDate = o.value;
 	   var isDutySelect = document.getElementById("isDuty");
 	   if(offDutyDate!=""){
 		   isDutySelect.options[1].selected = true; //否
 	   }
 	   else{
 		   isDutySelect.options[0].selected = true; //是
 	   }
    }
    
    //ajax的二级联动，使用选择的所属单位，查询该所属单位下对应的单位名称列表
    function findJctUnit(o){
    	//货物所属单位的文本内容
    	var jct = $(o).find("option:selected").text();
    	$.post("elecUserAction_findJctUnit.do",{"jctID":jct},function(data,textStatus){
	   	    //先删除单位名称的下拉菜单，但是请选择要留下
	   	    $("#jctUnitID option").remove();
	        if(data!=null && data.length>0){
	            for(var i=0;i<data.length;i++){
	   		       	var ddlCode = data[i].ddlCode;
	   		       	var ddlName = data[i].ddlName;
	   		       	//添加到单位名称的下拉菜单中
	   		       	var $option = $("<option></option>");
	   		       	$option.attr("value",ddlCode);
	   		       	$option.text(ddlName);
	   		       	$("#jctUnitID").append($option);
	   	        }
	        }
        });
    	
    }
	
</script>
</head>

  
 <body>
    <form name="Form1" method="post">	
    <br>
    
    <table cellSpacing="1" cellPadding="5" width="680" align="center" bgColor="#eeeeee" style="border:1px solid #8ba7e3" border="0">

	 <tr>
		<td class="ta_01" align="center" colSpan="4" background="${pageContext.request.contextPath }/images/b-info.gif">
		 <font face="宋体" size="2"><strong>编辑用户</strong></font>
		</td>
    </tr>
       <input name="userID"  type="hidden" value="ff808081110677790111070ccffe0001">
     <tr>
         <td align="center" bgColor="#f5fafe" class="ta_01">登&nbsp;&nbsp;录&nbsp;&nbsp;名：
         <td class="ta_01" bgColor="#ffffff">
         	<input name="logonName" type="text" maxlength="25" id="logonName"  value="zhangsan"  size=20 readonly="true">
         <font color="#FF0000">*</font></td>
         <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">用户姓名：
		 <td class="ta_01" bgColor="#ffffff">
		 	<input name="userName" type="text"  value="张三" maxlength="25" id="userName"  size=20>
		 	
         <font color="#FF0000">*</font></td>
    </tr>
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">性别：</td>
		<td class="ta_01" bgColor="#ffffff">
			<select name="sexID" id="sexID" style="width:155px">
			<option value=""></option>
			<option value="1" selected>男</option>
			<option value="2">女</option>
			</select>
			<font color="#FF0000">*</font>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">职位：</td>
		<td class="ta_01" bgColor="#ffffff">
			<select name="postID" id="postID" style="width:155px">
	    		<option value=""></option>
			    <option value="1" selected>总经理</option>
			    <option value="2">部门经理</option>
			    <option value="3">员工</option>
			</select>
			<font color="#FF0000">*</font>
		</td>
	</tr>
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">所属单位：</td>
		<td class="ta_01" bgColor="#ffffff">
			<select name="jctID" id="jctID" style="width:155px">
			<option value=""></option>
			
			<option value="1">北京</option>	
		
			<option value="2">上海</option>	
			
			<option value="3" selected="selected">深圳</option>	
			
			<option value="4">厦门</option>	
			
			<option value="5">成都</option>	
			
			<option value="6">海尔滨</option>	
			
			<option value="7">长春</option>	
			
			<option value="8">沈阳</option>	
			
			<option value="9">广州</option>	
			
			<option value="10">西安</option>	
			
			<option value="11">南宁</option>	
			
			<option value="12">天津</option>	
			
			<option value="13">海南</option>	
			
			</select> 
			<font color="#FF0000">*</font>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">单位名称：</td>
		<td class="ta_01" bgColor="#ffffff">
			<select id="jctUnitID" name="jctUnitID" style="width:155px">
				<option value="1">深圳华强电力公司</option>
			</select>
			<font color="#FF0000">*</font>
		</td>
	</tr>
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">密码：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="logonPwd" id="logonPwd" type="password" value="sunhy" maxlength="25"  size="22">
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">确认密码：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="passwordconfirm" type="password" value="sunhy" maxlength="25" size="22">
		</td>
	</tr>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">出生日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="birthday" id="birthday" type="text" maxlength="50"  size=20 value="" onClick="WdatePicker()">
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">联系地址：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="address" type="text" maxlength="50"  size=20 value="">
		</td>
	</tr>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">联系电话：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="contactTel" type="text" maxlength="25" size=20 value="88888888">
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">手机：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="mobile" type="text" maxlength="25"  size=20 value="">
		</td>
	</tr>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">电子邮箱：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="email" type="text" maxlength="50"  size=20 value="">
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">是否在职：</td>
		<td class="ta_01" bgColor="#ffffff">
			<select name="isDuty" id="isDuty"  style="width:155px">
				<option value="1" selected>是</option>
				<option value="2">否</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">入职日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="onDutyDate" id="onDutyDate" type="text" maxlength="50" size=20 value="2011-10-10" onClick="WdatePicker()">
			<font color="#FF0000">*</font>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">离职日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<input name="offDutyDate" id="offDutyDate" type="text" maxlength="50" size=20 value="" onClick="WdatePicker()" onblur="checkIsDuty(this)">
		</td>
	</tr>
	<TR>
		<TD class="ta_01" align="center" bgColor="#f5fafe">备注：</TD>
		<TD class="ta_01" bgColor="#ffffff" colSpan="3">
			<textarea name="remark"  style="WIDTH:92%;"  rows="4" cols="52">系统管理员</textarea>
		</TD>
		</TR>
		<TR>
		<td  align="center"  colSpan="4"  class="sep1"></td>
	</TR>
	<tr>
		<td class="ta_01" style="WIDTH: 100%" align="center" bgColor="#f5fafe" colSpan="4">
			<input type="button" id="BT_Submit" name="BT_Submit" value="保存"  style="font-size:12px; color:black; height=22;width=55"  onClick="check_null()">
		    <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
		    <input style="font-size:12px; color:black; height=22;width=55" type="button" value="关闭"  name="Reset1"  onClick="window.close()">
	    </td>
	</tr>
</table>　
</form>

</body>
</html>
