package service;
import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cffex.demo.advanced.bean.Chapter;
import com.cffex.demo.bean.Chapter;
import com.cffex.framework.annotation.Forbid;
import com.cffex.framework.annotation.Gateway;
import com.cffex.framework.annotation.SessionAttribute;
@Gateway
@Forbid(forbiddenMethods = {"toBeForbided1","toBeForbided2"})
public interface ChapterService {

	//根据pageNum和pageSize查询分页数据
	public List<Chapter> findAllByPageNum(int pageNum, int pageSize);

	public List<Chapter> findByTitle(String title, int pageNum, int pageSize);

	//展示如何在service中使用session
	public String getSessionContent();

	//展示session中的值如何作为函数的参数
	public String getUserIdInSession(@SessionAttribute("userId") String userId);

	//展示session中的值如何作为函数的参数，当函数中存在@SessionAttribute注解的参数时，参数要作为�?后的�?个或几个参数�?
	public String getUserIdInSession(String param, @SessionAttribute("userId") String userId);

	//获取configure.properties中的�?
	public String getConfigProperties();

	//调用ChapterService
	public List<Chapter> callService();

	//上传文件
	public void uploadFile(List<File> files);

	//上传文件附带其他参数
	//public void uploadFile(List<File> files, String param1, int param2);

	//下载文件
	public File downloadFile(String fileName);

	//展示禁止该函数的对外访问权限
	public String toBeForbided1();

	//展示禁止该函数的对外访问权限
	public String toBeForbided2();

	//展示如何记录日志
	public String loggerDemo();
}
