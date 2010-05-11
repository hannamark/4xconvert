--The good news is it appears that the registry user's login name is only used to determine the user's full name
--so as long as we properly update the login names we should be fine with exisiting registry accounts.
--The below sql is the basis for what will need to be run to manaully migrate those accounts.
--One thing to note, we will need to enable grid login in registry before we migrate over.
UPDATE CSM_USER SET LOGIN_NAME = 'NEW_LOGIN_NAME', PASSWORD = '' where LOGIN_NAME = 'OLD_LOGIN_NAME';