package com.support.repository;

import com.support.pojo.community_service;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName community_serviceRepository
 * @Author 吴俊淇
 * @Date 2020/3/20 15:01
 * @Version 1.0
 **/
public interface community_serviceRepository extends CrudRepository<community_service, Integer> {
    @Query(value = "select count(*) from community_service where user_id=?1 and status=?2", nativeQuery = true)
    Integer findAllByUserIdAndStatus(Integer userId, Integer status);

    List<community_service> findAllByUserId(Integer userId);

    @Query(value = "SELECT a.* from community_service a INNER JOIN " +
            "(SELECT c.community_service_id from community_service c where c.user_id=?1 ORDER BY c.community_service_id desc LIMIT ?2,?3)" +
            " b on a.community_service_id=b.community_service_id", nativeQuery = true)
    List<community_service> findAllPageByUserId(Integer userId, Integer number, Integer page);

    @Modifying
    @Query(value = "update community_service set status=?1 where community_service_id=?2", nativeQuery = true)
    Integer updateStatusByServiceId(Integer status, Integer community_service_id);

    @Query(value = "select count(*) from community_service where user_id=?1 and type='生活护理' GROUP BY user_id", nativeQuery = true)
    Integer findByUserIdAndHuLi(Integer userId);

    @Query(value = "select count(*) from community_service where user_id=?1 and type='家政保洁' GROUP BY user_id", nativeQuery = true)
    Integer findByUserIdAndBaoJie(Integer userId);

    @Query(value = "select count(*) from community_service where user_id=?1 and type='物业维修' GROUP BY user_id", nativeQuery = true)
    Integer findByUserIdAndWeiXiu(Integer userId);

    @Query(value = "select count(*) from community_service where user_id=?1 and type='陪聊' GROUP BY user_id", nativeQuery = true)
    Integer findByUserIdAndPeiLiao(Integer userId);

    @Query(value = "SELECT cs.user_id,count(*) as number from community_info ci INNER JOIN community_service cs on ci.id=cs.community_id where cs.community_id=?1 and cs.status =?2 GROUP BY cs.user_id", nativeQuery = true)
    List<Map<String,Object>> findByCommunityIdAndStatus(Integer id, Integer status);
}
