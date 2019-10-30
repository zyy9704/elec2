
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ page language="java" pageEncoding="UTF-8"%>

<table cellspacing="0" cellpadding="0" width="90%" align="center" bgcolor="#f5fafe" border="0">
    <TR>
        <TD align="center" background="${pageContext.request.contextPath }/images/cotNavGround.gif" width=25><img src="${pageContext.request.contextPath }/images/yin.gif" width="15"></TD>
        <TD class="DropShadow" background="${pageContext.request.contextPath }/images/cotNavGround.gif" width=80>站点信息列表</TD>
        <td class="ta_01" align="right" >
            <input type="button" name="BT_Search" style="font-size:12px; color:black; height=20;width=50" value="查询" id="BT_Search" onclick="gotoquery('/siteInfoPartPage.html')" >

            <input style="font-size:12px; color:black; height=20;width=50" id="BT_Add" type="button" value="添加" name="BT_Add" onclick="openWindow('/addSiteInfo.html','700','350')" />
            <input type="button" name="excelimport" style="font-size:12px; color:black; height=20;width=50"    value="导入"    id="excelimport"  onclick="openWindow('/ImportSiteInfoFile.html?fn=1','600','400')" >
            <input type="button" name="excelExport" style="font-size:12px; color:black; height=20;width=50"    value="导出"    id="excelExport"  onClick="exeportExcel()" >
            <input type="button" name="setexcelExport" style="font-size:12px; color:black; height=20;width=80"  value="导出设置" id="setexcelExport"  onClick="openWindow('/exportSiteInfoSetting.html','600','400')"  >

        </td>
    </TR>
</table>
<table cellspacing="1" cellpadding="0" width="90%" align="center" bgcolor="#f5fafe" border="0">
    <tr>
        <td class="ta_01" align="center" bgcolor="#f5fafe">
            <table cellspacing="0" cellpadding="1" rules="all" bordercolor="gray" border="1" id="DataGrid1" style="BORDER-RIGHT:gray 1px solid; BORDER-TOP:gray 1px solid; BORDER-LEFT:gray 1px solid; WIDTH:100%; WORD-BREAK:break-all; BORDER-BOTTOM:gray 1px solid; BORDER-COLLAPSE:collapse; BACKGROUND-COLOR:#f5fafe; WORD-WRAP:break-word">
                <tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
                    <td align="center" width="5%"  height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">序号</td>
                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">所属单位</td>

                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">归属地</td>

                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">站点代号</td>
                    <td align="center" width="13%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">站点名称</td>
                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">站点类别</td>
                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">通讯方式</td>
                    <td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">安装地点</td>

                    <td width="7%" align="center" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">编辑</td>
                    <td width="7%" align="center" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">删除</td>

                </tr>
                <%--<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">--%>

                <%--<td align="center" width="5%">&nbsp;1</td>--%>
                <%--<td align="center" width="10%">北京</td>--%>

                <%--<td align="center" width="10%">maradona</td>--%>

                <%--<td align="center" width="10%">maradona</td>--%>
                <%--<td align="center" width="13%" style="HEIGHT: 22px">--%>
                <%--<a href="#" onclick="openWindow('siteInfoView.jsp?editflag=0&stationid=4028d0931a519a19011a519e52e30001','700','350');" class="cl_01">--%>
                <%--maradona</a></td>--%>
                <%--<td align="center" width="10%">国内遥控站</td>--%>
                <%--<td align="center" width="10%">maradona</td>--%>
                <%--<td align="center" width="10%">maradona</td>--%>

                <%--<td align="center" style="HEIGHT: 22px">--%>
                <%--<a href="#" onclick="openWindow('siteInfoEdit.jsp?editflag=1&stationid=4028d0931a519a19011a519e52e30001','700','350');">--%>
                <%--<img src="${pageContext.request.contextPath }/images/edit.gif" border="0" style="CURSOR:hand"></a></td>--%>
                <%--<td align="center" style="HEIGHT: 22px">--%>
                <%--<a href="javascript:gotopage('removestation.do?stationid=4028d0931a519a19011a519e52e30001','nowpage')" onclick="returnMethod()">--%>
                <%--<img src="${pageContext.request.contextPath }/images/delete.gif" width="15" height="14" border="0" style="CURSOR:hand"></a></td>--%>

                <%--</tr>--%>

                <c:forEach items="${pageBean.beans}" var="pageBean" varStatus="vs">
                    <tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">



                        <td align="center" width="5%">&nbsp;${pageBean.stationid}</td>
                        <td align="center" width="10%">${pageBean.jctid}</td>

                        <td align="center" width="10%">${pageBean.attributionground}</td>

                        <td align="center" width="10%">${pageBean.stationcode}</td>
                        <td align="center" width="13%" style="HEIGHT: 22px">
                            <a href="#" onclick="openWindow('siteInfoView.jsp?editflag=0&stationid=4028d0931a519a19011a519e52e30001','700','350');" class="cl_01">
                                    ${pageBean.stationname}</a></td>
                        <td align="center" width="10%">${pageBean.stationtype}</td>
                        <td align="center" width="10%">${pageBean.contacttype}</td>
                        <td align="center" width="10%">${pageBean.jcfrequency}</td>

                        <td align="center" style="HEIGHT: 22px">
                            <a href="#" onclick="openWindow('siteInfoEdit.html?stationId=${pageBean.stationid}','700','350');">
                                <img src="${pageContext.request.contextPath }/images/edit.gif" border="0" style="CURSOR:hand"></a></td>
                        <td align="center" style="HEIGHT: 22px">
                            <a href="javascript:gotopage('deleteSiteInfo.html?stationid=${pageBean.stationid}','nowpage')" onclick="returnMethod()">
                                <img src="${pageContext.request.contextPath }/images/delete.gif" width="15" height="14" border="0" style="CURSOR:hand"></a></td>

                    </tr>
                </c:forEach>


            </table>
        </td>
    </tr>
    <tr>
        <td width="100%" height="1"  >
            <table border="0" width="100%" cellspacing="0" cellpadding="0">

                <%--<tr>--%>
                <%--<td width="28%" align="left">总记录数：${pageBean.totalResult} 条</td>--%>
                <%--<td width="15%" align="right"></td>--%>

                <%--<td width="5%" align="center">首页&nbsp;&nbsp;|</td>--%>
                <%--<td width="7%" align="center">上一页&nbsp;&nbsp;&nbsp;|</td>--%>




                <%--<td width="7%" align="center"><u><a href="#" onClick="gotopage('stationInfo.do','next')">下一页&nbsp;&nbsp;&nbsp;|</a></u></td>--%>
                <%--<td width="5%" align="center"><u><a href="#" onClick="gotopage('stationInfo.do','end')">末页</a></u></td>--%>

                <%--<td width="6%" align="center">第1页</td>--%>
                <%--<td width="6%" align="center">共3页</td>--%>
                <%--<td width="18%" align="right">至第<input size="1" type="text" name="goPage" >页--%>

                <%--<u><a href="#" onClick="gotopage('stationInfo.do','go')">确定</a></u></td>--%>
                <%--<td width="3%"></td>--%>
                <%--<td><input type="hidden" name="pageNO" value="1" ></td>--%>
                <%--<td><input type="hidden" name="prevpageNO" value="0"></td>--%>
                <%--<td><input type="hidden" name="nextpageNO" value="2"></td>--%>
                <%--<td><input type="hidden" name="querysql" value=""></td>--%>
                <%--<td><input type="hidden" name="sumPage" value="3" ></td>--%>
                <%--<td><input type="hidden" name="pageSize" value="" ></td>--%>
                <%--</tr>--%>

                <tr>
                    <td width="28%" align="left">总记录数：${pageBean.totalResult}</td>
                    <td width="15%" align="right"></td>

                    <td width="5%" align="center"><u><a href="javascript:gotopage('${pageBean.requestUrl}','start')">首页&nbsp;&nbsp;</a></u></td>
                    <td width="7%" align="center"><u><a href="javascript:gotopage('${pageBean.requestUrl}','prev')">上一页&nbsp;&nbsp;&nbsp;</a></u></td>




                    <td width="7%" align="center"><u><a href="javascript:gotopage('${pageBean.requestUrl}','next')">下一页&nbsp;&nbsp;&nbsp;|</a></u></td>
                    <td width="5%" align="center"><u><a href="javascript:gotopage('${pageBean.requestUrl}','end')">末页</a></u></td>

                    <td width="6%" align="center">第${pageBean.pageNo}页</td>
                    <td width="6%" align="center">共${pageBean.sumPage}页</td>
                    <td width="18%" align="right">至第<input size="1" type="text" name="goPage"  size="3"  style="width:50px">页



                        <u><a href="#" onClick="javascript:gotopage('${pageBean.requestUrl}','go')">确定</a></u></td>
                    <td width="3%"></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>

                    <td> <input type="hidden" name="typeView" value="" >
                        <input type="hidden" name="direction" value="">
                        <input type="hidden" name="pageNO" value="${pageBean.pageNo}" >
                        <input type="hidden" name="sumPage" value="${pageBean.sumPage}" >
                        <input type="hidden" name="prevpageNO" value="${(pageBean.pageNo-1)<=1?1:(pageBean.pageNo-1)}" >
                        <input type="hidden" name="nextpageNO" value="${(pageBean.pageNo+1)>=pageBean.sumPage?pageBean.sumPage:(pageBean.pageNo+1)}" >
                        <input type="hidden" name="lastRecordIndex" value="${pageBean.totalResult}" >
                        <input type="hidden" name="changeFlag" value="receive" ></td>
                </tr>
            </table>
        </td>
    </tr>
</table>