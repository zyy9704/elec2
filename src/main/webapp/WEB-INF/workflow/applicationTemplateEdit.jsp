<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
	<title>编辑申请模板</title>
	<link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.js"></script>
	<script type="text/javascript">
		function saveTemplate(){
			var name = $("#name").val();
			if($.trim(name) == ""){
				alert("模板名称不能为空！");
				$("#name")[0].focus();
				return false;
			}
			var processDefinitionKey = $("#processDefinitionKey").val();
			if($.trim(processDefinitionKey) == ""){
				alert("请选择一个流程定义的key值！");
				$("#processDefinitionKey")[0].focus();
				return false;
			}
			var upload = $("#upload").val();    
			var extension = upload.substr(upload.indexOf("."));
			//upload等于空表示不上传新的文件，仍然使用之前的文件
			if(upload != ""){
				if(extension != ".doc" && extension != ".docx"){
					alert("上传的文件后缀名必须是doc格式或者是docx格式！");
					return false;
				}
			}
			$("form:first").submit(); 
		}
	</script>
</head>
<body>
		<form name="Form1" action="workflow/elecApplicationTemplateAction_update.do" method="post" enctype="multipart/form-data">
			<br>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<input type="hidden" name="id"/>
				<input type="hidden" name="path"/>
				<tr>
					<td class="ta_01" align="center"
						background="${pageContext.request.contextPath }/images/b-info.gif"
						colspan="4">
						<font face="宋体" size="2"><strong>文档模板信息</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="left" bgColor="#ffffff" colspan="4">
						<font face="宋体" size="2">
							说明：<br />
								1，模板文件是doc扩展名的文件（Word文档）。<br />
								2，如果是添加：必须要选择模板文件。<br />
								3，如果是修改：只是在 需要更新模板文件时 才选择一个文件。
						</font>
					</td>
				</tr>
				<tr height=10>
					<td colspan=4></td>
				</tr>
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">模板名称：<font color="#FF0000">*</font></td>
			        <td class="ta_01" bgColor="#ffffff" colspan="3">
			        	<input type="text" name="name" size="20" value="设备购置计划模板" id="name"/>
			        </td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">流程定义的Key值：<font color="#FF0000">*</font></td>
			        <td class="ta_01" bgColor="#ffffff" colspan="3">
			         	<select name="processDefinitionKey" id="processDefinitionKey" style="width:155px">
						    <option value=""></option>
						    <option value="设备购置计划流程" selected="selected">设备购置计划流程</option>
						    <option value="设备费用报销流程">设备费用报销流程</option>
						</select>
	            	</td>
				</tr>
				
				<tr>
					<td align="center" bgColor="#f5fafe" class="ta_01">请选择模板文件(doc格式)：<font color="#FF0000">*</font></td>
			        <td class="ta_01" bgColor="#ffffff" colspan="3">
			        	<input type="file" name="upload" id="upload" style="width:450px;" /> 
			        </td>
				</tr>
				<tr height=50>
					<td colspan=4></td>
				</tr>
				<tr height=2>
					<td colspan=4
						background="${pageContext.request.contextPath }/images/b-info.gif"></td>
				</tr>
				<tr height=10>
					<td colspan=4></td>
				</tr>
				<tr>
					<td align="center" colspan=4>
						<input type="button" name="BT_Submit" value="保存"  style="font-size:12px; color:black; height=22;width=55"   onClick="saveTemplate()">
						<INPUT type="button" name="Reset1" id="save" value="关闭"
							onClick="opener.location.reload(); window.close();"
							style="width: 60px; font-size: 12px; color: black;">
					</td>
				</tr>
			</table>
		</form>

</body>
</html>
	