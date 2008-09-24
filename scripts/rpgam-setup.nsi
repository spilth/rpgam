; The name of the installer
Name "RPG Audio Mixer"

!ifndef VERSION
	!define VERSION A.B.C
!endif

; The file to write
outfile "..\dist\installers\windows\rpgam-${VERSION}.exe"
 
; The default installation directory
InstallDir "$PROGRAMFILES\RPG Audio Mixer"

; Registry key to check for directory (so if you install again, it will 
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\RPG_Audio_Mixer" "Install_Dir"

# create a default section.
section "RPG Audio Mixer"

	SectionIn RO

	; Set output path to the installation directory.
 	SetOutPath $INSTDIR
	File /r "..\dist\layout\windows\"
	
	; Write the installation path into the registry
	WriteRegStr HKLM SOFTWARE\RPG_Audio_Mixer "Install_Dir" "$INSTDIR"
	
	; Write the uninstall keys for Windows
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RPGAudioMixer" "DisplayName" "RPG Audio Mixer"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RPGAudioMixer" "UninstallString" '"$INSTDIR\uninstall.exe"'
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RPGAudioMixer" "NoModify" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RPGAudioMixer" "NoRepair" 1
	WriteUninstaller "uninstall.exe"

    ; read the value from the registry into the $0 register
    ;readRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" CurrentVersion
 
    ; print the results in a popup message box
    ;messageBox MB_OK "version: $0"

sectionEnd

Section "Start Menu Shortcuts"
  CreateDirectory "$SMPROGRAMS\RPG Audio Mixer"
  CreateShortCut "$SMPROGRAMS\RPG Audio Mixer\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\RPG AUdio Mixer\RPG Audio Mixer.lnk" "$INSTDIR\rpgam.exe" "" "$INSTDIR\rpgam.exe" 0
SectionEnd

Section "Uninstall"
  
	; Remove registry keys
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\RPGAudioMixer"
	DeleteRegKey HKLM SOFTWARE\RPG_Audio_Mixer

 	; Remove files and uninstaller
	Delete $INSTDIR\rpgam.exe
	Delete $INSTDIR\uninstall.exe

	; Remove shortcuts, if any
	Delete "$SMPROGRAMS\RPG Audio Mixer\*.*"

	; Remove directories used
	RMDir "$SMPROGRAMS\RPG Audio Mixer"
	RMDir "$INSTDIR"

SectionEnd

