### 拉取项目运行注意事项

1. 管理员运行IDE

2. Maven同步后执行

   ```cmd
   mvn compile
   ```



### DLL注入失败或无法控制游戏中鼠标的可能原因

1. 未以管理员运行软件
2. 缺少系统库，安装[64位Visual C++ Redistributable](https://aka.ms/vs/17/release/vc_redist.x64.exe) 和 [32位Visual C++ Redistributable](https://aka.ms/vs/17/release/vc_redist.x86.exe)