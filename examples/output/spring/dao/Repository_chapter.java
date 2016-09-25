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

@Named(value="ChapterRepository")
@EntityClass(entityClass=Chapter.class)
@Gateway
public interface ChapterRepository extends CrudRepository<Chapter, Long>,
		FrameworkRepository<Chapter, Long> {

	//根据Id查询数据
	public Chapter findById(Long Id);

	//根据title字段查询
	public List<Chapter> findByTitle(String title);

	//实体类的变量名中包含大写字母的情�?
	public List<Chapter> findByCreatorId(Long creatorId);

	//like查询
	public List<Chapter> findByTitleLike(String title);

	//containing查询
	public List<Chapter> findByTitleContaining(String title);

	//自定义SQL查询
	@Query("select t from Chapter t,Creator c where t.creatorId = c.id and c.name = :creatorName")
	public List<Chapter> findToDosByCreatorName(@Param("creatorName")String creatorName);

	//分页查询全部数据
	public Iterable<Chapter> findAll(Pageable pageable);

	//根据title字段分页查询
	public Page<Chapter> findByTitle(String title, Pageable pageable);

	//分页查询由某�?creator建立的数�?
	public Page<Chapter> findByCreatorId(Long creatorId, Pageable pageable);

}
