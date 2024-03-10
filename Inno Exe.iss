[Setup]
AppName=AlemanPluming
AppVersion=1.0
DefaultDirName={userdocs}\AlemanSoft
DefaultGroupName=AlemanSoft
OutputDir=Output
OutputBaseFilename=AlemanPlumingSetup
Compression=lzma
SolidCompression=yes
SetupIconFile=icon.ico

[Files]
Source: "AlemanSoft.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "icon.ico"; DestDir: "{app}"; Flags: ignoreversion

Source: "javafx\*"; DestDir: "{app}\javafx"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion   recursesubdirs createallsubdirs
Source: "AlemanPluming.jar"; DestDir: "{app}"; Flags: ignoreversion
[Icons]
Name: "{group}\AlemanSoft"; Filename: "{app}\AlemanSoft.bat"; WorkingDir: "{app}" ; IconFilename: "{app}\icon.ico"
Name: "{commondesktop}\AlemanSoft"; Filename: "{app}\AlemanSoft.bat"; WorkingDir: "{app}";      IconFilename: "{app}\icon.ico"



