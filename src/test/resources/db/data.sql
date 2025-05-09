INSERT INTO tasks (id, title, description, status, priority, author_id, performer_id)
VALUES ('11111111-1111-1111-1111-111111111111', 'Разработать API', 'Создать REST API для системы задач', 'IN_PROGRESS',
        'HIGH', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
       ('22222222-2222-2222-2222-222222222222', 'Написать тесты', 'Покрыть код unit-тестами', 'PENDING', 'MEDIUM',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
       ('33333333-3333-3333-3333-333333333333', 'Рефакторинг', 'Улучшить структуру кода', 'PENDING', 'LOW',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
       ('44444444-4444-4444-4444-444444444444', 'Деплой', 'Развернуть приложение на сервере', 'COMPLETED', 'HIGH',
        'cccccccc-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
       ('55555555-5555-5555-5555-555555555555', 'Документация', 'Написать документацию API', 'IN_PROGRESS', 'MEDIUM',
        'dddddddd-dddd-dddd-dddd-dddddddddddd', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
       ('66666666-6666-6666-6666-666666666666', 'Интеграция', 'Интегрировать с внешним API', 'PENDING', 'HIGH',
        'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),
       ('77777777-7777-7777-7777-777777777777', 'Дизайн', 'Создать UI/UX дизайн', 'COMPLETED', 'LOW',
        'ffffffff-ffff-ffff-ffff-ffffffffffff', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
       ('88888888-8888-8888-8888-888888888888', 'Аналитика', 'Проанализировать метрики', 'IN_PROGRESS', 'MEDIUM',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
       ('99999999-9999-9999-9999-999999999999', 'Мониторинг', 'Настроить систему мониторинга', 'PENDING', 'HIGH',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
       ('00000000-0000-0000-0000-000000000000', 'Безопасность', 'Добавить аутентификацию', 'COMPLETED', 'HIGH',
        'cccccccc-cccc-cccc-cccc-cccccccccccc', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee');

INSERT INTO comments (id, text, author_id, task_id)
VALUES
('10000000-0000-0000-0000-000000000001', 'Нужно добавить документацию', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
 '11111111-1111-1111-1111-111111111111'),
('10000000-0000-0000-0000-000000000002', 'Уже работаю над этим', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
 '11111111-1111-1111-1111-111111111111'),
('10000000-0000-0000-0000-000000000003', 'Проверил код, выглядит хорошо', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
 '11111111-1111-1111-1111-111111111111'),
('10000000-0000-0000-0000-000000000004', 'Есть вопросы по реализации', 'dddddddd-dddd-dddd-dddd-dddddddddddd',
 '11111111-1111-1111-1111-111111111111'),
('10000000-0000-0000-0000-000000000005', 'Готово к ревью', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
 '11111111-1111-1111-1111-111111111111'),

('20000000-0000-0000-0000-000000000001', 'Тесты не проходят', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
 '22222222-2222-2222-2222-222222222222'),
('20000000-0000-0000-0000-000000000002', 'Исправил ошибки', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
 '22222222-2222-2222-2222-222222222222'),
('20000000-0000-0000-0000-000000000003', 'Нужно больше тестов', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
 '22222222-2222-2222-2222-222222222222'),
('20000000-0000-0000-0000-000000000004', 'Покрытие 80% достигнуто', 'dddddddd-dddd-dddd-dddd-dddddddddddd',
 '22222222-2222-2222-2222-222222222222'),
('20000000-0000-0000-0000-000000000005', 'Готово к мерджу', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
 '22222222-2222-2222-2222-222222222222'),

('30000000-0000-0000-0000-000000000001', 'Рефакторинг необходим', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
 '33333333-3333-3333-3333-333333333333'),
('30000000-0000-0000-0000-000000000002', 'Улучшил структуру кода', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
 '33333333-3333-3333-3333-333333333333'),
('30000000-0000-0000-0000-000000000003', 'Есть замечания', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
 '33333333-3333-3333-3333-333333333333'),
('30000000-0000-0000-0000-000000000004', 'Проверьте изменения', 'dddddddd-dddd-dddd-dddd-dddddddddddd',
 '33333333-3333-3333-3333-333333333333'),
('30000000-0000-0000-0000-000000000005', 'Все ок, можно мерджить', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
 '33333333-3333-3333-3333-333333333333'),

('40000000-0000-0000-0000-000000000001', 'Деплой успешен', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
 '44444444-4444-4444-4444-444444444444'),
('40000000-0000-0000-0000-000000000002', 'Есть проблемы с сервером', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
 '44444444-4444-4444-4444-444444444444'),
('40000000-0000-0000-0000-000000000003', 'Все работает отлично', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
 '44444444-4444-4444-4444-444444444444'),
('40000000-0000-0000-0000-000000000004', 'Нужно добавить мониторинг', 'dddddddd-dddd-dddd-dddd-dddddddddddd',
 '44444444-4444-4444-4444-444444444444'),
('40000000-0000-0000-0000-000000000005', 'Задача завершена', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
 '44444444-4444-4444-4444-444444444444'),

('50000000-0000-0000-0000-000000000001', 'Документация почти готова', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
 '55555555-5555-5555-5555-555555555555'),
('50000000-0000-0000-0000-000000000002', 'Проверьте примеры кода', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
 '55555555-5555-5555-5555-555555555555'),
('50000000-0000-0000-0000-000000000003', 'Добавил новые разделы', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
 '55555555-5555-5555-5555-555555555555'),
('50000000-0000-0000-0000-000000000004', 'Нужно больше деталей', 'dddddddd-dddd-dddd-dddd-dddddddddddd',
 '55555555-5555-5555-5555-555555555555'),
('50000000-0000-0000-0000-000000000005', 'Готово к публикации', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
 '55555555-5555-5555-5555-555555555555');