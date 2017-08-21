package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.RoleUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final BeanPropertyRowMapper<RoleUser> ROW_MAPPER_ROLES = BeanPropertyRowMapper.newInstance(RoleUser.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;
    private final SimpleJdbcInsert insertRole;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertRole = new SimpleJdbcInsert(dataSource)
                .withTableName("user_roles")
                .usingGeneratedKeyColumns("user_id");


        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User result = DataAccessUtils.singleResult(users);
        if (result == null) return result;
        Set<Role> current = getRoles(id);
        result.setRoles(current);
        return result;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User result = DataAccessUtils.singleResult(users);
        if (result == null) return result;
        Set<Role> current = getRoles(result.getId());
        result.setRoles(current);
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> result = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> setRoles = getRoles();
        result.forEach(u -> u.setRoles(setRoles.get(u.getId())));
        return result;
    }

    private Map<Integer, Set<Role>> getRoles() {
        List<RoleUser> roles = jdbcTemplate.query("SELECT * FROM user_roles", ROW_MAPPER_ROLES);
        Map<Integer, Set<Role>> data = new HashMap<>();
//        for (RoleUser roleUser : roles) {
//            if (data.containsKey(roleUser.getUser_id())) {
//                data.get(roleUser.getUser_id()).add(Role.valueOf(roleUser.getRole()));
//            } else {
//                data.put(roleUser.getUser_id(), EnumSet.of(Role.valueOf(roleUser.getRole())));
//            }
//        }
        roles.forEach(ur -> data.computeIfAbsent(ur.getUser_id(), set -> new HashSet()).add(Role.valueOf(ur.getRole())));
        return data;
    }

    private Set<Role> getRoles(int id) {
        List<RoleUser> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", ROW_MAPPER_ROLES, id);
        return roles.stream().map(roleUser -> Role.valueOf(roleUser.getRole())).collect(Collectors.toSet());
    }
}
