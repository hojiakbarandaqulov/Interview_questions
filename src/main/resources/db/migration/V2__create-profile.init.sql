INSERT INTO profile (id, name, surname, phone,email, password, role, status, visible, created_date)
VALUES ('e58ed763-928c-4155-bee9-fdbaaadc15f3', 'Ali', 'Aliyev', '998992431068','codeuz91@gmail.com', '81dc9bdb52d04dc2036dbd8313ed055',
        'ROLE_ADMIN', 'ACTIVE', true, now()) ON CONFLICT (id) DO NOTHING;