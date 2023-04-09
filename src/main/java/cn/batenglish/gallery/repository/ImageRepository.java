package cn.batenglish.gallery.repository;

import org.springframework.data.repository.CrudRepository;

import cn.batenglish.gallery.entity.Image;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ImageRepository extends CrudRepository<Image, Integer> {
//    @Query("SELECT e FROM images e WHERE e.upload_time >= :since")
//    public List<Image> findAllByUpload_TimeSince(@Param("since") Timestamp since);
    public Optional<Image> findById(Integer id);
    public List<Image> findAllByUploadTimeGreaterThan(Timestamp since);
    public List<Image> findAllByUploaderIdAndUploadTimeGreaterThan(Integer userId,Timestamp since);
}