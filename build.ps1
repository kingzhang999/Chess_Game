# 打包成可双击运行的 jar。
#
# 用法（在项目根目录）：
#   pwsh -File .\build.ps1
#
# 产出：项目根目录下的 Chess_Game.jar，双击即可运行（需已安装 Java 20 或更高版本）。
# 贴图与初始摆法都会打进 jar，运行时不依赖 resource 目录。

[CmdletBinding()]
param(
    [string]$JarName = 'Chess_Game.jar',
    [string]$MainClass = 'BackgroundThings.GameScreen'
)

$ErrorActionPreference = 'Stop'

$root = $PSScriptRoot
$sources = Join-Path $root 'src'
$resources = Join-Path $root 'resource'
$buildDir = Join-Path $root '.build'
$classesDir = Join-Path $buildDir 'classes'
$manifest = Join-Path $buildDir 'MANIFEST.MF'
$jarPath = Join-Path $root $JarName

Write-Host '==> 汇总源文件'
$sourceFiles = Get-ChildItem -Path $sources -Recurse -Filter *.java | ForEach-Object { $_.FullName }
if ($sourceFiles.Count -eq 0) {
    throw "没有在 $sources 下找到 .java 文件"
}
Write-Host ("    共 {0} 个源文件" -f $sourceFiles.Count)

Write-Host '==> 清理旧的构建目录'
if (Test-Path $buildDir) { Remove-Item -Recurse -Force $buildDir }
New-Item -ItemType Directory -Path $classesDir -Force | Out-Null

Write-Host '==> 编译'
$compileOutput = & javac -encoding UTF-8 -d $classesDir $sourceFiles 2>&1
if ($LASTEXITCODE -ne 0) {
    $compileOutput | ForEach-Object { Write-Host $_ }
    throw '编译失败'
}
$compileOutput | Where-Object { $_ -match '警告|warning' } | ForEach-Object { Write-Host "    $_" }

Write-Host '==> 复制资源到输出目录（resource 下的内容会打进 jar 根路径）'
Copy-Item -Path $resources -Destination $classesDir -Recurse -Force

Write-Host '==> 生成 MANIFEST.MF'
@(
    'Manifest-Version: 1.0'
    "Main-Class: $MainClass"
    'Created-By: Chess_Game build.ps1'
    ''
) | Set-Content -Path $manifest -Encoding ascii

Write-Host "==> 打包 $JarName"
if (Test-Path $jarPath) { Remove-Item -Force $jarPath }
$jarOutput = & jar --create --file $jarPath --manifest $manifest -C $classesDir . 2>&1
if ($LASTEXITCODE -ne 0) {
    $jarOutput | ForEach-Object { Write-Host $_ }
    throw '打包失败'
}

$size = [math]::Round((Get-Item $jarPath).Length / 1KB, 1)
Write-Host ''
Write-Host "完成：$jarPath（$size KB）"
Write-Host "运行：java -jar `"$jarPath`"，或直接双击该文件"
Write-Host ''
Write-Host 'jar 内容概览：'
& jar --list --file $jarPath |
    Where-Object { $_ -match '\.class$' } |
    Group-Object { ($_ -split '/')[0] } |
    Sort-Object Name |
    ForEach-Object { Write-Host ("    {0,-20} {1,3} 个类" -f $_.Name, $_.Count) }
& jar --list --file $jarPath |
    Where-Object { $_ -match '^resource/' } |
    Measure-Object |
    ForEach-Object { Write-Host ("    {0,-20} {1,3} 个资源文件" -f 'resource/', $_.Count) }
