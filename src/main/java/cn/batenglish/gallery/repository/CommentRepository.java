package cn.batenglish.gallery.repository;

import cn.batenglish.gallery.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findAllByTargetImageIdEquals(Integer imageId);

}
