package cn.edu.cdu.lab.service;

import java.util.List;

import cn.edu.cdu.lab.model.Thesis;

public interface ThesisService {
	
	/**
	 * 根据id获得一个论文
	 * @param id 论文id
	 * @return
	 */
	public Thesis getThesis(int id);
	
	/**
	 * 得到分页显示的论文
	 * @param offset跳过多少行
	 * @param obtain取多少行
	 * @param teacherId教师Id
	 * @return
	 */
	public List getThesis(Integer checkState,int offset, int obtain, String teacherId);
	
	/**
	 * 获得页数
	 * @param obtain 每页多少行
	 * @return
	 */
	public Integer getPageCount(Integer checkState,int obtain ,String teacherId);
	
	/**
	 * 保存一个论文
	 * @param thesis
	 * @throws Exception 
	 */
	public void saveThesis(Thesis thesis) throws Exception;
	
	/**
	 * 删除一篇论文
	 * @param id 论文id
	 */
	public void deleteThesis(int id);
	
	/**
	 * 审核通过一篇论文
	 * @param id 论文id
	 */
	public void getVerify(int id);
	
	/**
	 * 更新一个论文
	 * @param thesis
	 */
	public void updateThesis(Thesis thesis);
	
	/**
	 * 根据老师的id得到该教师上传的论文
	 * @param teacherId
	 * @return
	 */
	public List getThesisBytTeacherId(String teacherId);
}















