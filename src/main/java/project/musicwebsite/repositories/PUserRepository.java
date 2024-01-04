//package project.musicwebsite.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//import project.musicwebsite.entity.PremiumPackage;
//import project.musicwebsite.entity.UPremium;
//
//
//public interface PUserRepository extends JpaRepository<UPremium,Long> {
//
//    @Transactional
//    @Modifying
//    @Query(
//            value = "update Usertbl set role=1,startTime=null,endTime=null where id = ?1",
//            nativeQuery = true
//    )
//    void switchToNormalUser(Long id);
//
//    @Transactional
//    @Modifying
//    @Query(
//            value = "delete from Registertbl where user_id=?1",
//            nativeQuery = true
//    )
//    void deleteAllRegisterByUserId(Long id);
//
//}
