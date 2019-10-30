package com.itszt.elec8.utils.doPage;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/*
   进行分页设置的工具类
 */
public class UtilPageBean<T> {
    //单页容量
    public static final int PAGE_CAPACITY=2;
    //HTTP请求
    private HttpServletRequest req;
    //每页的记录数
    private int pageSize;
    //当前页
    private int currentPageNo = 1;
    //开始记录数
    private int beginResult = 0;
    //总记录数
    private int totalResult = 0;
    //总页数
    private int totalPage = 0;
    //某一页的数据
    private List<T> beans=new ArrayList<>();

    private PageBean<T> page=null;

    public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        getPageBean().setBeans(beans);
        this.beans = beans;
    }


    //日志
   // protected  static final Logger log = Logger.getLogger("testLogger");

    /**
     * 初始化
     * @param req HttpServletRequest HTTP请求
     */
    public UtilPageBean(HttpServletRequest req) {
       this.currentPageNo = req.getParameter("pageNO")!=null && !req.getParameter("pageNO").equals("")?new Integer(req.getParameter("pageNO")).intValue():1;

       if(this.pageSize<=0){
    	   
    	   this.pageSize=PAGE_CAPACITY;
    	   
       }
       this.req = req;      
    }
    
    public UtilPageBean(){
    	this.currentPageNo=1;
    	this.pageSize=PAGE_CAPACITY;
    	
    }
    public UtilPageBean(int currentPageNo){
    	
    	this.currentPageNo=currentPageNo;
    	this.pageSize=PAGE_CAPACITY;
    	
    }
    /**
     * 计算总页数
     */
    private void countPages() {
        if(totalResult==0) {
            this.totalPage=1;
        }
        else {
            this.totalPage = (totalResult / pageSize); //总共几页
            if ((totalResult % pageSize) != 0) this.totalPage = this.totalPage + 1;
        }
    }

    private boolean isFirstPage(){
    	
    	if(this.currentPageNo<=1){
    		
    		return true;
    	}else
    	{
    		return false;	
    	}	
    }

    private boolean isLastPage(){
    	
    	if(this.currentPageNo>=this.totalPage){
    		
    		return true;
    	}else
    	{
    		return false;	
    	}	
    }
    
    /**
     * 获得当前页
     * @return int 当前页
     */
    private int getCurrentPageNo() {
          return currentPageNo;
    }

    /**
     * 获得每页的记录数
     * @return int
     */
    private int getPageSize() {
        return pageSize;
    }

    /**
     * 获得总记录数
     * @return int
     */
    private int getTotalResult() {
        return totalResult;
    }

    /**
     * 设置当前页
     * @param current int 当前页码
     */
    private void setCurrentPageNo(int current) {
        this.currentPageNo  =current;
    }

    /**
     * 设置每页的记录数
     * @param i int 记录数
     */
    private void setPageSize(int i) {
        this.pageSize = i;
    }



    /**
     * @param i
     */
    private void setBeginResult(int i) {
        this.beginResult = i;
    }

    /**
     * 获得总页数
     * @return int 总页数
     */
    private int getTotalPage() {
        
    	//log.info("run here totalPage:"+totalPage);
        return totalPage;
        
    }





    /**
     * 设置序号
     */
    private void setRequestValue(){    	
    	page=new PageBean();
    	page.setFirstPage(isFirstPage());
    	page.setLastPage(isLastPage()); 		
        page.setPageNo(currentPageNo);	
    	page.setPageSize(pageSize);
    	page.setSumPage(totalPage);
    	page.setTotalResult(totalResult);
    	page.setBeans(beans);

    }

    private void setTotalPage(int totalPage){
    	
    	this.totalPage=totalPage;
    }

    public PageBean getPageBean(){

        return page;

    }

    /**
     * @param totalResult
     *            The totalResult to set. 设置该分页信息总共有多少条记录
     */
    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
        countPages();
    }

    /**
     * 获得开始记录数
     * @return int 开始记录数
     */
    public int getBeginResult() {

        if(totalPage!=1) {
            if(currentPageNo>=totalPage){
            currentPageNo=totalPage;
            beginResult= (currentPageNo - 1) * pageSize;
            pageSize=totalResult-beginResult;
        }else{

            beginResult = (currentPageNo - 1) * pageSize;
        }
        }
        if(totalPage==1)
        {
            currentPageNo=totalPage;

            beginResult=0;
            pageSize=totalResult;

        }
        setRequestValue();
        return beginResult;
    }
  
}
