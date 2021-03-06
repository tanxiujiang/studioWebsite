/**
 * @Title ManageArticleAction.java
 * @Package cn.edu.cdu.lab.action
 * @Description [简要描述本文件的作用] 本文件是后台管理员操作文章管理
 * @author 李华 【643444070@qq.com】
 * @Date 2013-3-12 16:58:10
 * @Version 1.0
 * 
 */
package cn.edu.cdu.lab.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.cdu.lab.model.Article;
import cn.edu.cdu.lab.model.Files;
import cn.edu.cdu.lab.model.Navigation;
import cn.edu.cdu.lab.service.ArticleManageService;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName: ManageConnectionAction
 * @Description: 管理员对文章管理
 * @author 李华 【643444070@qq.com】
 * @date 2013-3-12 16:58:30
 * 
 */
public class ManageArticleAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Gson gson;//通用的json对象
	private ArticleManageService articleManageService;// 采用spring实现依赖注入
	String temp = "";//附件的url
	// 构造函数依赖注入
	public ManageArticleAction(ArticleManageService articleManageService) {
		this.articleManageService = articleManageService;
	}

	// 设计到添加文章，采用model传值方式
	private Article article;//文章的基本信息
	private File upload;//文章添加的附件信息
	String uploadFileName;//文件的原始名称
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	 
	/**
	 * 获取原始的connection信息
	 * 
	 * @return 获取成功页面
	 * @throws IOException
	 */
	//无任何条件的加载所有记录
	public String show() throws IOException {
		init(); // 获取基本的对象
		//分页显示属性设置
		// 加载所有的文章信息
		List<Article> articleList = articleManageService.showAll();
		request.setAttribute("articleList", articleList);
		//loadFirstNavigation();
		return "showSuccess";
	}

	// 加载一级导航菜单 采用jquery的ajax实现
	public void  loadFirstNavigation() throws IOException {
		init(); // 获取基本的对象
		PrintWriter out = response.getWriter();// 获取输出流对象
		List<Navigation> navigationList = articleManageService.loadFirstNavigation();
		String navigationString = gson.toJson(navigationList); //返回的一级导航信息
		out.println(navigationString);
		out.flush();
		out.close();
	}

	// 加载二级导航的菜单列表
	// 可以重复使用加载三级导航的菜单列表
	public void loadSecondNavigation() throws IOException {
		init(); // 获取基本的对象
		PrintWriter out = response.getWriter();// 获取输出流对象
		int  parentId =  Integer.valueOf(request.getParameter("id"));//获取二级导航的的父级ID
		List<Navigation> navigationList = articleManageService.loadSecondNavigation(parentId);
		String navigationString = gson.toJson(navigationList); //返回的是二级导航信息
		out.println(navigationString);
		out.flush();
		out.close();
	}

	// 根据一级导航的点击加载二级导航

	
	/**
	 * 添加文章, 1：有附件的时候，处理文件上传情况，上传成功则把整个数据信息插入数据库
	 * 2: 判断上传文件，没有附件的时候，则直接封装url为空的文章实体插入数据库
	 * 3: 有文件的话同时完成对文件信息表的插入操作
	 * @throws IOException 
	 */
	//添加文章内容
	
	public  String  addArticle(){
		
		init();//初始化基本对象
		/*if(upload !=null)//执行上传文件操作，并且在文件表中保存文件的信息
		{
			boolean flag = addFile();
			if(flag)//上传文件和保存文件数据都成功
			{
				//保存文章信息,首先添加页面还不够的信息
				article.setFileUrl(temp);
				article.setTimes(0);//上传次数初始化为0次，后面直接更改数据的默认值
				//时间格式化操作，可以避免在显示的时候还去格式化来显示
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				article.setCreateTime(sdf.format(new Date()));
				HttpSession session = request.getSession();
				//暂时是假数据
				article.setRoleId("110");
				article.setRoleName("谭秀江");
				article.setRoleType("教师");
				//保存文章对象
				articleManageService.saveArticle(article);
				request.setAttribute("message", "文章发表成功 ^_^");
				return "continueAddArticle";
			}
		}
		else
		{*/
			article.setFileUrl("url");//暂时是假数据，不确定是否需要..................
			article.setTimes(0);//上传次数初始化为0次，后面直接更改数据的默认值
			//时间格式化操作，可以避免在显示的时候还去格式化来显示
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			article.setCreateTime(sdf.format(new Date()));
			//从session中获取值存入文章对象中
			HttpSession session = request.getSession();
			//暂时是假数据
			article.setRoleId(session.getAttribute("USER_ID").toString());
			article.setRoleName(session.getAttribute("USER_NAME").toString());
			//16 管理员  8老师     4学生    2注册人员
			if(session.getAttribute("USER_TYPE").toString().equals("16"))
			{
				article.setRoleType("管理员");
			}
			else
			{
				article.setRoleType("管理员");
			}
			article.setRoleType(session.getAttribute("USER_TYPE").toString());
			
			//保存文章对象
			articleManageService.saveArticle(article);
			//返回到当前页面提示新增文章成功
			request.setAttribute("message", "文章发表成功 ^_^");
			//return "continueAddArticle";
		//}
		//request.setAttribute("message", "文章发表发表失败 -_-");
		return "continueAddArticle";
	}
	/*
	 * 完成文件上传并且在文件表中添加上传文件信息
	 */
	public boolean addFile()
	{
		
		InputStream in;
		try {
			
			in = new FileInputStream(getUpload());
			//获取文件的输入流
		    /**
		     * 保存文件
		     */
			//获取本项目的根目录
			//ActionContext ac = ActionContext.getContext(); 
			//ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT); 
			temp = ServletActionContext.getRequest().getRealPath("");
			temp+="\\Files\\"+uploadFileName;//文件保存在服务器端得位置以及文件的名字
			System.out.println("服务器路径1:"+temp);
			String aa = request.getSession().getServletContext().getRealPath("/");
			System.out.println("获取路径信息2："+aa);
			OutputStream out = new FileOutputStream(temp);
			//建立一个缓冲区
			byte buffer[]  = new byte[1024];
			int count = 0;
			while((count = in.read(buffer))>0)
			{
				out.write(buffer, 0, count);//把存放在缓冲区里面的数据流存入保存目录当中
			}
			out.close();
			in.close();

			/**
			 * 文件上传成功之后继续保存文件信息到file表中保存
			 */
			//获取文件的各种属性
			
			Files file = new Files();
			file.setTitle(article.getTitle());//设置成文章标题
			file.setFileName(uploadFileName);
			file.setDownloaderRole(0);//基于文章展示的附件，公开性   0 表示完全公开
			//file.setFileUrl("./Files/"+uploadFileName);//下载的相对路径
			file.setFileUrl(temp);//下载的绝对路径
			//从session中获取
			file.setUploaderId(article.getAuthor());//保存上传者的姓名
			//uploaderRole
			file.setUploaderRole(0);//先设置为0，到时候和集成之后在session中去获取
			file.setFileName("陈亮");
			
			file.setChecks(0);//暂时填写0，表示不需要审核
			file.setSize(String.valueOf(upload.length()));//文件的大小
			file.setType(0);//这是附件的内型，先默认为0
			file.setDownloads(0);//文件的下载次数，第一长传则下载次数为0
			//调用service方法实现对文件信息的保存
			boolean flag = articleManageService.saveFile(file);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public String append() {

		return "addArticleSuccess";
	}
	public void init() {
		gson = new Gson();
		ActionContext act = ActionContext.getContext();
		request = (HttpServletRequest) act
				.get(ServletActionContext.HTTP_REQUEST);
		response = (HttpServletResponse) act
				.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("text/html");// 设置返回相应格式，很重要
		response.setCharacterEncoding("utf-8");
	}

	/**
	 * 判断输入字符串是否为空，为空就返回""
	 * 
	 * @param string
	 * @return string
	 */
	public String isStringOrNull(String string) {
		return string == null ? "" : string;
	}
	/**
	 * 删除文章信息
	 */
	public void deleteOneArticle() throws IOException
	{
		/**
		 *  删除文章需要做：1.判断需要删除对象的fileURL是否存在，如果不为空，则取出来先删掉对象的文件
		 *  2.删除文件成功再继续删除对应的文章信息
		 *  3.也许需要做的是，抓取文章中插入插件当中的图片信息的路径，删除其中的图片文件
		 */
		init();//实例化基本对象，获取request对象
		int id = Integer.valueOf(request.getParameter("deleteId"));//文章的id主键
		//int pageNow = Integer.valueOf(request.getParameter("pageNow"));
		//获取文章内容，抓取图片路径
		Article article = articleManageService.loadOneArticle(id);
		
		//抓取文章内容中的src和href地址  删除图片和文件
		this.deleteFile(article.getContent());
		
		
		//根据获取需要删除的数据对象
		boolean flag = articleManageService.deleteOneArticle(id);
		
		/*开始做的跳转误区删除痕迹
		 * if(flag)
		{
			ArticleBypage(pageNow);
		}*/
		//return "showArticleIframe";
		PrintWriter out = response.getWriter();
		//如果删除成功
		if(flag)
		{
			//ajax
			out.println("OK");
			out.flush();
			out.close();
		}
	}
	
	//编辑文章
	public String editArticle()
	{
		init();
		int id = article.getId();//获取显示页面的id属性
		//从service实现类调用加载一篇文章的方法实现加载
		Article articleFromD = articleManageService.loadOneArticle(id);//需要更新的数据从数据库中首先拉出来，然后修改成表单提交数据更新
		//从页面映射的实体类中加载需要更新的数据
		articleFromD.setAuthor(article.getAuthor());//修改作者
		articleFromD.setContent(article.getContent());
		articleFromD.setFileUrl("---");//假的文件url
		articleFromD.setTitle(article.getTitle());
		//加载指定的文章内容
		//保存文章信息,首先添加页面还不够的信息
		articleManageService.updateArticle(articleFromD);
		
		// 方式一：加载当前刚更新的数据显示在编辑页面的当中 这里不是从数据拿出来的文章信息，而是暂时存在ation的article变量
		//request.setAttribute("article", article);
		
		//方式二：再一次根据id属性从数据库加载刚刚更新的文章信息
		Article article = articleManageService.loadOneArticle(id);
		request.setAttribute("article", article);
		request.setAttribute("flag", "文章修改成功!");
		//返回到当前编辑页面
		return "articelAdd";//重新返回到编辑页面
	}
	//编辑文章之前的加载单个文章信息
	public String loadOneArticle()
	{
		init();//初始化基本对象
		int id = Integer.valueOf(request.getParameter("id"));//根据id属性加载需要编辑的文章，显示在编辑页面
		Article article = articleManageService.loadOneArticle(id);
		request.setAttribute("article", article);
		return "showEditArticle";
	}
	//根据导航信息来是动态加载文章列表
	public  String loadArticleByNavi()
	{
		init();
		//获取导航的id属性
		int firstId = Integer.valueOf(request.getParameter("firstId"));
		int secondId = Integer.valueOf(request.getParameter("secondId"));
		int thirdId = Integer.valueOf(request.getParameter("thirdId"));
		//设置分页显示数据的基本属性
		int pageNow = 1;
		int pageSize = 8;
		int pageCount = 0;//对应的记录条数
		List<Article>  articleList = new ArrayList<Article>();
		
		if(request.getParameter("pageNow") != null){
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		articleList = articleManageService.loadArticleByPage_Navi(firstId, secondId, thirdId, pageSize*(pageNow - 1), pageSize);
		request.setAttribute("articleList", articleList);
		pageCount = articleManageService.getArticleCountByNavi(firstId, secondId, thirdId, pageSize);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNow", pageNow);
		
		//动态改变查询字符串的url
		String queryString = "manageArticleAction_loadArticleByNavi?firstId="+firstId+"&secondId="+secondId+"&thirdId="+thirdId+"&";
		request.setAttribute("paging", queryString);
		return "showArticleIframe";
	}
	
	
	//直接加载
	public String ArticleBypage()
	{
		init();
		//设置分页显示数据的基本属性
		int pageNow = 1;
		int pageCount = 0;
		int pageSize = 6;
		List<Article>  articleList = new ArrayList<Article>();
		if(request.getParameter("pageNow") != null){
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		articleList = articleManageService.ArticleBypage(Article.class, pageSize*(pageNow - 1), pageSize, null);
		pageCount = articleManageService.getArticleCount(Article.class,pageSize);
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNow", pageNow);
		//动态改变查询字符串的url
		request.setAttribute("paging", "manageArticleAction_ArticleBypage?");//这个是根据不同条件的分页返回不同的分页查询字符串
		return "showArticleIframe";
	}
	
	
	
	//需要传入参数pageNow的加载,主要是删除后的定位
	public String ArticleBypage(int nowPage)
	{
		init();
		//设置分页显示数据的基本属性
		int pageNow = 1;
		int pageCount = 0;
		int pageSize = 8;
		List<Article>  articleList = new ArrayList<Article>();
		pageNow = nowPage;
		articleList = articleManageService.ArticleBypage(Article.class, pageSize*(pageNow - 1), pageSize, null);
		pageCount = articleManageService.getArticleCount(Article.class,pageSize);
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNow", pageNow);
		return "showArticleIframe";
	}
	public String toIframe()
	{
		return "showSuccess";
	}
	//收索功能实现
	public String searchArticleByTitle() throws UnsupportedEncodingException
	{
		init();
		//设置分页显示数据的基本属性
		int pageNow = 1;
		int pageCount = 0;
		int pageSize = 8;
		//解决了中文乱码
		String title_key = new String(request.getParameter("keywords").getBytes("ISO-8859-1"),"utf-8");
		if(request.getParameter("pageNow") != null)
		{
			pageNow = Integer.valueOf(request.getParameter("pageNow"));
		}
		List<Article>  articleList = new ArrayList<Article>();
		articleList = articleManageService.searchArticleByTitle(title_key,pageSize*(pageNow - 1), pageSize);
		pageCount = articleManageService.getArticleCountByKeyWords(title_key, pageSize);
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNow", pageNow);
		//动态改变查询字符串的url
		String queryString = "manageArticleAction_searchArticleByTitle?keywords="+title_key+"&";
		request.setAttribute("paging", queryString);
		return "showArticleIframe";
	}
	//删除文章的时候获取文章中的图片和附件的URL进行删除操作 参数就是获取出来的路径
	//根据传入的路径删除指定的目录后者文件，无论存在与否，   urlPth 要删除的目录或者文件
	// 删除成功返回true 否则返回false
	public void deleteFile(String urlPath)
	{
		//删除图片信息
		List<String> imgNameList = getDeleteImgPath(urlPath);
		//删除文件信息
		List<String> fileNameList = getDeleteFilePath(urlPath);
		
		System.out.println("图片数量："+imgNameList.size());
		
		System.out.println("文件数量："+fileNameList.size());
		//删除文章中的图片
		for(int i=0; i < imgNameList.size(); i++)
		{
			//拼接物理目录
			String deleteUrl = request.getSession().getServletContext().getRealPath("") +"\\attached\\image\\"+imgNameList.get(i);
			File file = new File(deleteUrl);
			//如果是文件 并且文件存在
			if(file.isFile() && file.exists())
			{
				//执行删除操作
				file.delete();
			}
		}	
		//删除文章中的文件
		for(int i=0; i < fileNameList.size(); i++)
		{
			//拼接物理目录
			System.out.println("返回图片的资源路径："+fileNameList.get(i));
			String deleteUrl = request.getSession().getServletContext().getRealPath("") +"\\attached\\file\\"+fileNameList.get(i);
			File file = new File(deleteUrl);
			//如果是文件 并且文件存在
			if(file.isFile() && file.exists())
			{
				//执行删除操作
				file.delete();
			}
		}	
			
	}
	//删除文章之前首先用正则表达式匹配里面上传文件的信息，包括图片和附件信息，也就是获取文件的名字
	public List<String> getDeleteImgPath(String string)
	{
		// 实现图片文件的匹配   正则表达式：$pattern="/<[img|IMG].*?src=[\'|\"](.*?(?:[\.gif|\.jpg]))[\'|\"].*?[\/]?>/";
		//实现上传附件的匹配 正则表达式： $pat = '/<a(.*?)href="(.*?)"(.*?)>(.*?)<\/a>/i'
		String RegImg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		List<String> listString = new ArrayList<String>();//返回的所有满足条件的文件名的集合
		Pattern pattern = Pattern.compile(RegImg);//图片路径匹配
		Matcher matcher = pattern.matcher(string);//匹配结果
		//遍历所有满足条件的字符串
		while(matcher.find())//指针方式寻找下一个
		{
			String src = matcher.group();//获取的是类似src = " ";
			//截取文件名
			int start  = src.lastIndexOf("/")+1;
			int end = src.length()-1;
			//继续拼接时间文件夹名字
			String imgName = src.substring(start, end);
			String dImgName = imgName.substring(0, 8)+"\\"+imgName;
			listString.add(dImgName);
		}
		return listString;//返回文件名的集合
	}
	
	public List<String> getDeleteFilePath(String string)
	{
		// 实现图片文件的匹配   正则表达式：$pattern="/<[img|IMG].*?src=[\'|\"](.*?(?:[\.gif|\.jpg]))[\'|\"].*?[\/]?>/";
		//实现上传附件的匹配 正则表达式： $pat = '/<a(.*?)href="(.*?)"(.*?)>(.*?)<\/a>/i'
		String Regfile = "href\\s*=\\s*\'?\"?([^(\\s\")]+)\\s*\'?\"";//获取文件的正则表达式
		List<String> listString = new ArrayList<String>();//返回的所有满足条件的文件名的集合
		Pattern pattern = Pattern.compile(Regfile);//图片路径匹配
		Matcher matcher = pattern.matcher(string);//匹配结果
		//遍历所有满足条件的字符串
		while(matcher.find())//指针方式寻找下一个
		{
			//现在取得的累类似 href = "";
			String src = matcher.group();
			int start = src.lastIndexOf("/")+1;
			int end = src.length()-1;
			//拼接自动生成的文件夹和文件名   时间+时间文件名
			String fileName = src.substring(start, end);
			String dFileName = fileName.substring(0, 8)+"\\"+fileName;
			listString.add(dFileName);
		}
		return listString;
	}
	
}
