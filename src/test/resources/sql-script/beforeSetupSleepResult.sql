/* Result SleepType 1 */
INSERT
  INTO result_sleep (RESULT_SEQ, ADMISSION_ID, RESULT_START_DATE, RESULT_START_TIME
                    ,RESULT_END_DATE, RESULT_END_TIME, SLEEP_TYPE, DEVICE_ID, REG_DT)
VALUES (999998, 'A123456789', '2021-12-06', '23:30:00', '2021-12-07', '00:30:00', '1', 'test', '2021-12-01 00:00:00');
/* Result SleepType 2 */
INSERT INTO result_sleep (RESULT_SEQ, ADMISSION_ID, RESULT_START_DATE, RESULT_START_TIME
                        ,RESULT_END_DATE, RESULT_END_TIME, SLEEP_TYPE, DEVICE_ID, REG_DT)
VALUES (999999, 'A123456789', '2021-12-07', '00:31:00', '2021-12-07', '01:30:00', '2', 'test', '2021-12-01 00:00:00');

