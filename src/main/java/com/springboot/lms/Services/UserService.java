package com.springboot.lms.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.lms.Dto.UserRegister;
import com.springboot.lms.ErrorAndExceptionHandling.InvalidUserData;
import com.springboot.lms.ErrorAndExceptionHandling.UnableToSaveData;
import com.springboot.lms.ErrorAndExceptionHandling.UserAlreadyExists;
import com.springboot.lms.Dto.CourseDto;
import com.springboot.lms.Dto.DepartmentDto;
import com.springboot.lms.Dto.RoleProjection;
import com.springboot.lms.Dto.UserProjection;
import com.springboot.lms.Models.Course;
import com.springboot.lms.Models.Department;
import com.springboot.lms.Models.Roles;
import com.springboot.lms.Models.User;
import com.springboot.lms.Repositories.CourseRepo;
import com.springboot.lms.Repositories.DepartmentRepo;
import com.springboot.lms.Repositories.RolesRepo;
import com.springboot.lms.Repositories.UserRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    //dependencies
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepo departmentRepo;
    private final CourseRepo courseRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserProjection user=userRepo.findUserByEmail(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found with email:"+username);
        List<SimpleGrantedAuthority> roles=new ArrayList<>();
        for(RoleProjection role:userRepo.findUserRolesById(user.getId())){
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .id(user.getId())
                .userAuthorities(roles)
                .build();
    }

    

    //user new registration
    public String userRegister(UserRegister userRegister){
        String temp=this.checkUserData(userRegister);
        if(!temp.equals(""))
            throw new InvalidUserData(temp);
        if(userRepo.findUserByEmail(userRegister.getEmail())!=null)
            throw new UserAlreadyExists("User Already Exists with email:"+userRegister.getEmail());
        try{
            //saving user data;

            Department department=this.getDepartment(userRegister.getDepartment());
            if(department==null)
                throw new InvalidUserData("Department does not exists");
            User user=new User();
            user.setRoles(this.getRoleObject(new HashSet<>(userRegister.getRoles())));
            user.setDepartment(department);
            user.setEmail(userRegister.getEmail());
            user.setFirstName(userRegister.getFirstName());
            user.setLastName(userRegister.getLastName());
            user.setRollNumber(userRegister.getRollNumber());
            user.setYear(userRegister.getYear());
            user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            this.userRepo.save(user);
            return "User Saved";
        }
        catch(Exception ex){
            throw new UnableToSaveData("Unable to save the user data");
        }
    }

    //return courses
    public List<CourseDto> getAvaliableCourses(int pageNumber,int pageSize){
        Pageable page=PageRequest.of(pageNumber, pageSize);
        List<CourseDto> result=new ArrayList<>();
        for(Course course:courseRepo.findAll(page)){
            CourseDto dto=new CourseDto();
            dto.setCourseCode(course.getCourseCode());
            dto.setCourseName(course.getCourseName());
            dto.setDuration(course.getDuration());
            dto.setEndDate(course.getStartDate().toString());
            dto.setEnrollmentCapacity(course.getEnrollmentCapacity());
            dto.setStartDate(course.getStartDate().toString());
            result.add(dto);
        }
        return result;
    }

    //returns the departments
    public List<DepartmentDto> getAvaliableDepartment(int pageNumber,int pageSize){
        Pageable page=PageRequest.of(pageNumber, pageSize);
        List<DepartmentDto> result=new ArrayList<>();
        for(Department department:departmentRepo.findAll(page)){
            DepartmentDto dto=new DepartmentDto();
            dto.setDepartmentCode(department.getDepartmentCode());
            dto.setDepartmentName(department.getDepartmentName());
            dto.setId(department.getId());
            result.add(dto);
        }
        return result;
    }

    public List<User> getStudents(int pageNumber,int pageSize){
        Pageable page=PageRequest.of(pageNumber, pageSize);
        return userRepo.findAllStudents(page);
    }

    public List<User> getFaculty(int pageNumber,int pageSize){
        Pageable page=PageRequest.of(pageNumber, pageSize);
        return userRepo.findAllFaculty(page);
    }

    private Department getDepartment(String depCode){
        return departmentRepo.findDepByCode(depCode);
    }

    //checks the user data valid or not
    private String checkUserData(UserRegister user){
        StringBuilder result=new StringBuilder("");
        if(user.getEmail()==null || user.getEmail().equals(""))
            result.append("Email is Mandatory filed$");
        if(user.getFirstName()==null || user.getFirstName().equals(""))
            result.append("First Name is missing$");
        if(user.getPassword()==null || user.getPassword().length()<8)
            result.append("Password Field is Missing or length is less then 8$");
        if(user.getRollNumber()==null || user.getRollNumber().length()<10)
            result.append("Roll number is missing$");
        if(user.getRoles()==null || user.getRoles().size()==0)
            result.append("Roles not specified$");
        return result.toString();
    }

    private List<Roles> getRoleObject(Set<String> roleNames){
        if((roleNames.contains("STUDENT") && roleNames.contains("FACULTY")) || (roleNames.contains("STUDENT") && roleNames.contains("ADMIN")))
            throw new InvalidUserData("Student role can't associate with ADMIN or FACULTY");
        List<Roles> roles=new ArrayList<>();
        for(String role:roleNames){
            roles.add(this.getOrCreate(role));
        }
        return roles;
    }
    private Roles getOrCreate(String roleName){
        Roles role=rolesRepo.findByRoleName(roleName);
        if(role==null){
            role=new Roles();
            role.setRoleName(roleName);
            rolesRepo.save(role);
        }
        return role;
    }
}
