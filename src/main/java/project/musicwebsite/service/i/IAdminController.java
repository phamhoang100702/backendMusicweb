package project.musicwebsite.service.i;

import project.musicwebsite.entity.Admin;

public interface IAdminController {
    Admin save(Admin admin);
    boolean isExisted(String email);
}
