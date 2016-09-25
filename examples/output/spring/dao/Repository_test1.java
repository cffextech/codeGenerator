package dao;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cffex.demo.advanced.bean.AdvancedToDo;
import com.cffex.framework.annotation.EntityClass;
import com.cffex.framework.annotation.Gateway;
import com.cffex.framework.customRepository.FrameworkRepository;

@Named(value="Test1Repository")
@EntityClass(entityClass=Test1.class)
@Gateway
public interface Test1Repository extends CrudRepository<Test1, Long>,
		FrameworkRepository<Test1, Long> {

	//根据Id查询数据
	public Test1 findById(Long Id);

	//根据title字段查询
	public List<Test1> findByTitle(String title);

	//实体类的变量名中包含大写字母的情�?
	public List<Test1> findByCreatorId(Long creatorId);

	//like查询
	public List<Test1> findByTitleLike(String title);

	//containing查询
	public List<Test1> findByTitleContaining(String title);

	//自定义SQL查询
	@Query("select t from Test1 t,Creator c where t.creatorId = c.id and c.name = :creatorName")
	public List<Test1> findToDosByCreatorName(@Param("creatorName")String creatorName);

	//分页查询全部数据
	public Iterable<Test1> findAll(Pageable pageable);

	//根据title字段分页查询
	public Page<Test1> findByTitle(String title, Pageable pageable);

	//分页查询由某�?creator建立的数�?
	public Page<Test1> findByCreatorId(Long creatorId, Pageable pageable);

}
