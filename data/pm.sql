if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_SPEC_FILE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PM_SPEC] DROP CONSTRAINT FK_SPEC_FILE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_ACCOUNT]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_ACCOUNT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_FUNCTION]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_FUNCTION]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_MEMBER]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_MEMBER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_RIGHT]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_RIGHT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_RIGHTDATA_PROJECT]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_RIGHTDATA_PROJECT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_RIGHTROLE]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_RIGHTROLE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_PM_BIGBUG]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_PM_BIGBUG]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_PM_PROGRAM]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_PM_PROGRAM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_PM_PROJECT]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_PM_PROJECT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[VIEW_MS_LOG]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [dbo].[VIEW_MS_LOG]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EQ_CUSTOMER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EQ_CUSTOMER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EQ_ORDER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EQ_ORDER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EQ_PRODUCT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EQ_PRODUCT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_ACCOUNT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_ACCOUNT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_ALLKIND]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_ALLKIND]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_CODESET]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_CODESET]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_FUNCTION]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_FUNCTION]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_MEMBER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_MEMBER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_OTP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_OTP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_RIGHT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_RIGHT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_RIGHTDATA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_RIGHTDATA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_RIGHTROLE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_RIGHTROLE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_ROLE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_ROLE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MS_USBKEY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MS_USBKEY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_APP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_APP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_BIGBUG]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_BIGBUG]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_FILE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_FILE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_HARD]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_HARD]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_HUMAN]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_HUMAN]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_ITEM]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_ITEM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_LOG]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_LOG]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_OUTPUT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_OUTPUT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_PROGRAM]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_PROGRAM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_PROJECT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_PROJECT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_PROJECT_PROPERTY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_PROJECT_PROPERTY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_PROJ_REQU]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_PROJ_REQU]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_REQUIREMENT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_REQUIREMENT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_RISK]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_RISK]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_SPEC]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_SPEC]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_STAKEHOLDER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_STAKEHOLDER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PM_TRACK]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PM_TRACK]
GO

CREATE TABLE [dbo].[EQ_CUSTOMER] (
	[CUST_ID] [TableID] NOT NULL ,
	[CUST_NAME] [C100] NULL ,
	[CUST_NM] [C50] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EQ_ORDER] (
	[ORD_ID] [TableID] NOT NULL ,
	[ORD_PID] [TableID] NULL ,
	[CUST_ID] [TableID] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EQ_PRODUCT] (
	[PROD_ID] [TableID] NOT NULL ,
	[PROD_NAME] [C500] NULL ,
	[PROD_SPEC] [C100] NULL ,
	[PROD_COLOR] [C50] NULL ,
	[PROD_UNIT] [C10] NULL ,
	[PROD_PID] [TableID] NULL ,
	[PROD_KIND] [TableID] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_ACCOUNT] (
	[ACCOUNT_ID] [TableID] NOT NULL ,
	[ACCOUNT_NAME] [C50] NULL ,
	[LOGIN_NAME] [C50] NULL ,
	[PASSWORD] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[NOTE] [C100] NULL ,
	[STATUS] [TableID] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_ALLKIND] (
	[ALLKIND_ID] [C50] NULL ,
	[ALLKIND_NO] [C20] NULL ,
	[ALLKIND_NAME] [C50] NULL ,
	[ALLKIND_NM] [C10] NULL ,
	[ALLKIND_PID] [C50] NULL ,
	[ALLKIND_SEQ] [C5] NULL ,
	[NOTE] [C100] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_CODESET] (
	[CODESETID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[DATASTATUS] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[CODENAME] [nvarchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[CODECONTENT] [nvarchar] (1000) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[NOTE] [nvarchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATAER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_FUNCTION] (
	[FUN_ID] [TableID] NOT NULL ,
	[PROG_ID] [TableID] NULL ,
	[FUN_NO] [C20] NULL ,
	[FUN_NAME] [C100] NULL ,
	[NOTE] [C200] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_MEMBER] (
	[MEMBER_ID] [TableID] NOT NULL ,
	[ACCOUNT_ID] [TableID] NULL ,
	[ROLE_ID] [TableID] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_OTP] (
	[OTP_ID] [TableID] NOT NULL ,
	[OTP_BARCODE] [TableID] NULL ,
	[ACCOUNT_ID] [TableID] NULL ,
	[OTP_AUTHKEY] [TableID] NULL ,
	[OTP_CURRSUCC] [bigint] NULL ,
	[OTP_CURRDFT] [int] NULL ,
	[NOTE] [C100] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_RIGHT] (
	[RIGHT_ID] [TableID] NOT NULL ,
	[PROG_ID] [TableID] NULL ,
	[FUN_ID] [TableID] NULL ,
	[ACCOUNT_ID] [TableID] NULL ,
	[ROLE_ID] [TableID] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_RIGHTDATA] (
	[RIGHTDATA_ID] [TableID] NOT NULL ,
	[ACCOUNT_ID] [TableID] NULL ,
	[TABLE_NAME] [C50] NULL ,
	[DATA_ID] [TableID] NULL ,
	[ROLE_ID] [TableID] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_RIGHTROLE] (
	[RIGHTROLE_ID] [TableID] NOT NULL ,
	[ROLE_ID] [TableID] NULL ,
	[FUN_ID] [TableID] NULL ,
	[DATAER] [TableID] NULL ,
	[DATATIME] [SystemTime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_ROLE] (
	[ROLE_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[ROLE_NAME] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[NOTE] [varchar] (200) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATAER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MS_USBKEY] (
	[USBKEY_ID] [TableID] NOT NULL ,
	[ACCOUNT_ID] [TableID] NULL ,
	[USBKEY_PATH] [C10] NULL ,
	[USBKEY_FILE1] [C10] NULL ,
	[USBKEY_FILE2] [C10] NULL ,
	[USBKEY_PASSWORD] [C100] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_APP] (
	[APP_ID] [TableID] NOT NULL ,
	[STATUS] [C50] NULL ,
	[APP_NAME] [C100] NULL ,
	[APP_NM] [C20] NULL ,
	[APP_NO] [C10] NULL ,
	[APP_NOTE] [C200] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_BIGBUG] (
	[BIG_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[PROJ_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[BIG_TITLE] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[BIG_START] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[BIG_END] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_FILE] (
	[FILE_ID] [TableID] NOT NULL ,
	[STATUS] [C50] NULL ,
	[FILE_VER] [C10] NULL ,
	[FILE_NAME] [C100] NULL ,
	[FILE_PATH] [C100] NULL ,
	[FILE_NOTE] [C100] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_HARD] (
	[HARD_ID] [TableID] NOT NULL ,
	[STATUS] [C50] NULL ,
	[HARD_IP] [C100] NULL ,
	[HARD_OWNER] [C20] NULL ,
	[HARD_CPU] [C10] NULL ,
	[HARD_MEM] [C20] NULL ,
	[HARD_SOUND] [C20] NULL ,
	[HARD_USB] [C20] NULL ,
	[HARD_CDROM] [C20] NULL ,
	[HARD_OS] [C20] NULL ,
	[HARD_BROWER] [C20] NULL ,
	[HARD_OFFICE] [C20] NULL ,
	[HARD_KEEPER] [C20] NULL ,
	[HARD_DISK] [C20] NULL ,
	[HARD_INTRNET] [C20] NULL ,
	[HARD_INTERNET] [C20] NULL ,
	[PROJ_NOTE] [C200] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_HUMAN] (
	[RISK_ID] [TableID] NOT NULL ,
	[STATUS] [C50] NULL ,
	[HUMAN_KIND] [C20] NULL ,
	[HUMAN_TITLE] [C50] NULL ,
	[HUMAN_SEX] [C200] NULL ,
	[HUMAN_ENAME] [C20] NULL ,
	[HUMAN_WORK] [C100] NULL ,
	[HUMAN_REL] [C50] NULL ,
	[HUMAN_MOBILE] [C20] NULL ,
	[HUMAN_TEL] [C20] NULL ,
	[HUMAN_FAX] [C20] NULL ,
	[HUMAN_EMAIL] [C100] NULL ,
	[HUMAN_HOUSE_NO] [C10] NULL ,
	[HUMAN_CAR_NO] [C10] NULL ,
	[HUMAN_ADDRESS] [C100] NULL ,
	[HUMAN_BIRTHDAY] [C10] NULL ,
            [HUMAN_SON] [C10] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_ITEM] (
	[ITEM_ID] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[STATUS] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_PID] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROJ_ID] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[FILE_ID] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_TITLE] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_KIND] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_CONTENT] [varchar] (500) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_PUTER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_PUT_TIME] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_HANDLER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_PLAN_TIME] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ITEM_REAL_TIME] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATAER] [nvarchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_LOG] (
	[LOG_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[LOG_OBJ] [C50] NULL ,
	[LOG_MSG] [varchar] (1000) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[LOG_CONTENT] [varchar] (5000) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[LOG_IP] [varchar] (20) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[ACCOUNT_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[LOG_KIND] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROJ_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATAER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_OUTPUT] (
	[OUT_ID] [TableID] NOT NULL ,
	[FILE_ID] [C50] NULL ,
	[PROJ_ID] [C50] NULL ,
	[STATUS] [C50] NULL ,
	[OUT_NAME] [C100] NULL ,
	[OUT_FUNCTION] [C100] NULL ,
	[OUT_NOTE] [C200] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_PROGRAM] (
	[PROG_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[PROG_PID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROJ_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROG_URL] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROG_NAME] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROG_CHECK] [varchar] (5) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[NOTE] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATAER] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_PROJECT] (
	[PROJECT_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[PROJ_NO] [varchar] (20) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROJ_NAME] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[PROJ_NM] [varchar] (20) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_PROJECT_PROPERTY] (
	[PRPR_ID] [TableID] NOT NULL ,
	[PROJ_ID] [TableID] NULL ,
	[STATUS] [C50] NULL ,
	[PRPR_ITEM] [C10] NULL ,
	[PRPR_NAME] [C20] NULL ,
	[PRPR_VALUE] [C10] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_PROJ_REQU] (
	[PRRE_ID] [TableID] NOT NULL ,
	[STATUS] [C50] NULL ,
	[PROJ_ID] [C50] NULL ,
	[REQU_ID] [C50] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_REQUIREMENT] (
	[REQU_ID] [TableID] NOT NULL ,
	[APP_ID] [C50] NULL ,
	[PROJ_ID] [C50] NULL ,
	[STAK_ID] [C50] NULL ,
	[STATUS] [C50] NULL ,
	[REQU_TITLE] [C100] NULL ,
	[REQU_NOTE] [C50] NULL ,
	[REQU_PUTER] [C50] NULL ,
	[EQU_PUT_TIME] [C10] NULL ,
	[REQU_HANDLER] [C50] NULL ,
	[REQU_PLAN_TIME] [DATE_TIME] NULL ,
	[REQU_REAL_TIME] [DATE_TIME] NULL ,
	[DATAER] [C50] NULL ,
	[DATA_TIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_RISK] (
	[RISK_ID] [TableID] NOT NULL ,
	[PROJ_ID] [C50] NULL ,
	[STATUS] [C50] NULL ,
	[RISK_KIND] [C20] NULL ,
	[RISK_TITLE] [C50] NULL ,
	[RISK_NOTE] [C200] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_SPEC] (
	[SPEC_ID] [TableID] NOT NULL ,
	[PROG_ID] [TableID] NULL ,
	[FILE_ID] [TableID] NULL ,
	[STATUS] [C50] NULL ,
	[SPEC_KIND] [C100] NULL ,
	[SPEC_ITEM] [C20] NULL ,
	[SPEC_CONTENT] [C500] NULL ,
	[IS_PAGE] [C5] NULL ,
	[SPEC_NOTE] [C200] NULL ,
	[SPEC_APPLYER] [C50] NULL ,
	[SPEC_CHECKER] [C50] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_STAKEHOLDER] (
	[STAK_ID] [TableID] NOT NULL ,
	[PROJ_ID] [C50] NULL ,
	[STATUS] [C50] NULL ,
	[STAK_KIND] [C20] NULL ,
	[STAK_NAME] [C50] NULL ,
	[STAK_SEQ] [C5] NULL ,
	[DATAER] [C50] NULL ,
	[DATATIME] [DATE_TIME] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PM_TRACK] (
	[TRACK_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL ,
	[BIG_ID] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[TRACK_DATE] [varchar] (10) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[TRACK_NAME] [varchar] (100) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[TRACK_RESULTs] [varchar] (200) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[TRACK_PIC] [varchar] (50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL ,
	[DATATIME] [datetime] NULL 
) ON [PRIMARY]
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_ACCOUNT].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_ALLKIND].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_FUNCTION].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_MEMBER].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_OTP].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_RIGHT].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_RIGHTDATA].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_SystemTime]', N'[MS_RIGHTROLE].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_APP].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_FILE].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_HARD].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_HUMAN].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_OUTPUT].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_PROJECT_PROPERTY].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_PROJ_REQU].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_REQUIREMENT].[DATA_TIME]'
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_REQUIREMENT].[REQU_PLAN_TIME]'
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_REQUIREMENT].[REQU_REAL_TIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_RISK].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_SPEC].[DATATIME]'
GO

setuser
GO

setuser
GO

EXEC sp_bindefault N'[dbo].[D_DATE_TIME]', N'[PM_STAKEHOLDER].[DATATIME]'
GO

setuser
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_LOG
AS
SELECT     TOP 100 PERCENT LOG_ID, LOG_MSG, LOG_CONTENT, LOG_IP, ACCOUNT_ID, LOG_KIND, PROJ_ID, DATAER, DATATIME
FROM         dbo.PM_LOG
ORDER BY DATATIME DESC

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_ACCOUNT
AS
SELECT     dbo.MS_ACCOUNT.ACCOUNT_ID, dbo.MS_ACCOUNT.ACCOUNT_NAME, dbo.MS_ACCOUNT.LOGIN_NAME, dbo.MS_ACCOUNT.PASSWORD, 
                      dbo.MS_ACCOUNT.NOTE, dbo.MS_ACCOUNT.STATUS, dbo.MS_ALLKIND.ALLKIND_NAME AS STATUS_NAME
FROM         dbo.MS_ACCOUNT INNER JOIN
                      dbo.MS_ALLKIND ON dbo.MS_ACCOUNT.STATUS = dbo.MS_ALLKIND.ALLKIND_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_FUNCTION
AS
SELECT     dbo.MS_FUNCTION.FUN_ID, dbo.MS_FUNCTION.PROG_ID, dbo.MS_FUNCTION.FUN_NO, dbo.MS_FUNCTION.FUN_NAME, dbo.MS_FUNCTION.NOTE, 
                      dbo.PM_PROGRAM.PROJ_ID, dbo.PM_PROGRAM.PROG_URL, dbo.PM_PROGRAM.PROG_NAME, dbo.PM_PROGRAM.PROG_CHECK
FROM         dbo.MS_FUNCTION INNER JOIN
                      dbo.PM_PROGRAM ON dbo.MS_FUNCTION.PROG_ID = dbo.PM_PROGRAM.PROG_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_MEMBER
AS
SELECT     dbo.MS_MEMBER.ACCOUNT_ID, dbo.MS_MEMBER.ROLE_ID, dbo.MS_ACCOUNT.ACCOUNT_NAME, dbo.MS_ACCOUNT.LOGIN_NAME, 
                      dbo.MS_ROLE.ROLE_NAME, dbo.MS_ROLE.NOTE, dbo.MS_MEMBER.MEMBER_ID
FROM         dbo.MS_MEMBER INNER JOIN
                      dbo.MS_ACCOUNT ON dbo.MS_MEMBER.ACCOUNT_ID = dbo.MS_ACCOUNT.ACCOUNT_ID INNER JOIN
                      dbo.MS_ROLE ON dbo.MS_MEMBER.ROLE_ID = dbo.MS_ROLE.ROLE_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_RIGHT
AS
SELECT     dbo.MS_RIGHT.RIGHT_ID, dbo.MS_RIGHT.FUN_ID, dbo.MS_RIGHT.ACCOUNT_ID, dbo.MS_RIGHT.ROLE_ID, dbo.MS_ROLE.ROLE_NAME, 
                      dbo.VIEW_MS_FUNCTION.FUN_NO, dbo.VIEW_MS_FUNCTION.FUN_NAME, dbo.VIEW_MS_FUNCTION.PROG_NAME, 
                      dbo.VIEW_MS_FUNCTION.PROG_URL, dbo.PM_PROGRAM.PROG_CHECK, dbo.VIEW_MS_FUNCTION.PROG_ID
FROM         dbo.PM_PROGRAM INNER JOIN
                      dbo.VIEW_MS_FUNCTION ON dbo.PM_PROGRAM.PROG_ID = dbo.VIEW_MS_FUNCTION.PROG_ID RIGHT OUTER JOIN
                      dbo.MS_RIGHT ON dbo.VIEW_MS_FUNCTION.FUN_ID = dbo.MS_RIGHT.FUN_ID LEFT OUTER JOIN
                      dbo.MS_ROLE ON dbo.MS_RIGHT.ROLE_ID = dbo.MS_ROLE.ROLE_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIES_MS_RIGHTDATA_PROJECT
AS
SELECT     dbo.PM_PROJECT.PROJ_NO, dbo.PM_PROJECT.PROJ_NAME, dbo.PM_PROJECT.PROJ_NM, dbo.MS_RIGHTDATA.RIGHTDATA_ID, 
                      dbo.MS_RIGHTDATA.ACCOUNT_ID, dbo.MS_RIGHTDATA.TABLE_NAME, dbo.MS_RIGHTDATA.DATA_ID, dbo.MS_RIGHTDATA.ROLE_ID
FROM         dbo.MS_RIGHTDATA INNER JOIN
                      dbo.PM_PROJECT ON dbo.MS_RIGHTDATA.DATA_ID = dbo.PM_PROJECT.PROJECT_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_MS_RIGHTROLE
AS
SELECT     dbo.MS_RIGHTROLE.RIGHTROLE_ID, dbo.MS_RIGHTROLE.ROLE_ID, dbo.MS_RIGHTROLE.FUN_ID, dbo.MS_ROLE.ROLE_NAME, 
                      dbo.MS_FUNCTION.PROG_ID, dbo.MS_FUNCTION.FUN_NO, dbo.MS_FUNCTION.NOTE, dbo.MS_FUNCTION.FUN_NAME, 
                      dbo.PM_PROGRAM.PROG_URL, dbo.PM_PROGRAM.PROG_NAME
FROM         dbo.MS_RIGHTROLE INNER JOIN
                      dbo.MS_ROLE ON dbo.MS_RIGHTROLE.ROLE_ID = dbo.MS_ROLE.ROLE_ID INNER JOIN
                      dbo.MS_FUNCTION ON dbo.MS_RIGHTROLE.FUN_ID = dbo.MS_FUNCTION.FUN_ID INNER JOIN
                      dbo.PM_PROGRAM ON dbo.MS_FUNCTION.PROG_ID = dbo.PM_PROGRAM.PROG_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_BIGBUG
AS
SELECT         dbo.PM_BIGBUG.PROJ_ID, dbo.PM_BIGBUG.BIG_TITLE, 
                          dbo.PM_BIGBUG.BIG_START, dbo.PM_BIGBUG.BIG_END, 
                          dbo.PM_PROJECT.PROJ_NAME, dbo.PM_PROJECT.PROJ_NM, 
                          dbo.PM_BIGBUG.BIG_ID
FROM             dbo.PM_BIGBUG INNER JOIN
                          dbo.PM_PROJECT ON 
                          dbo.PM_BIGBUG.PROJ_ID = dbo.PM_PROJECT.PROJECT_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_PM_PROGRAM
AS
SELECT     dbo.PM_PROGRAM.PROG_ID, dbo.PM_PROGRAM.PROG_PID, dbo.PM_PROGRAM.PROJ_ID, dbo.PM_PROGRAM.PROG_URL, 
                      dbo.PM_PROGRAM.PROG_NAME, dbo.PM_PROGRAM.PROG_CHECK, dbo.PM_PROGRAM.NOTE, dbo.PM_PROJECT.PROJ_NO, 
                      dbo.PM_PROJECT.PROJ_NAME, dbo.PM_PROJECT.PROJ_NM
FROM         dbo.PM_PROGRAM INNER JOIN
                      dbo.PM_PROJECT ON dbo.PM_PROGRAM.PROJ_ID = dbo.PM_PROJECT.PROJECT_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO

CREATE VIEW dbo.VIEW_PM_PROJECT
AS
SELECT     dbo.PM_PROJECT.PROJECT_ID, dbo.PM_PROJECT.PROJ_NO, dbo.PM_PROJECT.PROJ_NAME, dbo.PM_PROJECT.PROJ_NM, RD.ACCOUNT_ID, 
                      dbo.PM_PROJECT.DATATIME
FROM         dbo.PM_PROJECT LEFT OUTER JOIN
                          (SELECT     DATA_ID, ACCOUNT_ID
                            FROM          dbo.MS_RIGHTDATA
                            WHERE      (TABLE_NAME = N'pm_project')) AS RD ON dbo.PM_PROJECT.PROJECT_ID = RD.DATA_ID

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

