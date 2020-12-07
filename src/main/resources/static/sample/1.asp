<%
'****************
'@ 랜덤 문자 생성 함수
'****************
Function Func_getRndChr(ByVal rLen)
Dim rtnStr
Randomize
For rIdx = 1 To rLen
    rtnStr = rtnStr & Chr(Int(2*Rnd)*32 + Int((90-65+1)*Rnd + 65))
Next
    Func_getRndChr = rtnStr
End Function
 
'**************
'@ 임시 파일명 생성
'**************
Set Fso = Server.CreateObject("Scripting.FileSystemObject")
 
tempName = Mid(Fso.GetTempName(),4,3)
tempRand = Func_getRndChr(16)
 
Set Fso = Nothing
 
'********************
'@ 파일 업로드 컴포넌트 설정
'********************
Set uf = Server.CreateObject("DEXT.FileUpload")
uf.DefaultPath = "D:\www\upload\SmartUpload\"
 
'******
'@ 변수
'******
url = uf("callback") & "?callback_func=" & uf("callback_func")
fileName = uf("Filedata").FileName
fileMime = uf("Filedata").MimeType
 
upFileName = tempRand & "N" & tempName & ".jpg" '@ 파일명 생성
 
'**********
'@ 업로드 처리
'**********
If fileName = "" Then '@ 업로드 파일 없을 경우
    url = url & "&errstr=error"
Else '@ 업로드 파일 있을 경우
    If fileMime = "image/gif" Or fileMime = "image/pjpeg" Or fileMime = "image/x-png" Or fileMime = "image/jpeg" Or fileMime = "image/png" Then '@ 필터링
        filePath = uf.DefaultPath & upFileName '@ 파일 경로
 
        uf("Filedata").SaveAs filePath '@ 업로드
         
        '@ 리턴 url
        url = url & "&bNewLine=true"
        url = url & "&sFileName=" & upFileName
        url = url & "&sFileURL=/upload/SmartUpload/" & upFileName
    Else
        url = url & "&errstr=" & upFileName
    End If
End If
 
Set uf = Nothing
 
Response.Write "<script type='text/javascript'>"
Response.Write "    window.location.href = '" & url & "';"
Response.Write "</script>"
Response.End
%> 
