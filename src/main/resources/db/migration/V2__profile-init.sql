INSERT INTO profile (id, name, surname, phone, password, role, status, visible, created_date)
VALUES ('e58ed763-928c-4155-bee9-fdbaaadc15f3', 'Ali', 'Aliyev', '+998992431068', 'e10adc3949ba59abbe56e057f20f883e',
        'ROLE_ADMIN', 'ACTIVE', true, now()) ON CONFLICT (id) DO NOTHING;
