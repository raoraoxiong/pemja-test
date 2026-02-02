# Pemja 验证程序

这是一个用于验证 Pemja 能否正常执行 Python 代码的测试程序。

## home path 构建
wget https://github.com/astral-sh/python-build-standalone/releases/download/20241016/cpython-3.9.20+20241016-x86_64-unknown-linux-gnu-install_only.tar.gz

export TEST_HOME=/data/home/rockyxiong/testsss
tar xvzf cpython-3.9.20+20241016-x86_64-unknown-linux-gnu-install_only.tar.gz -C $TEST_HOME


## 启动命令
打包：mvn clean package

运行：
java -cp pemja-test-1.0-SNAPSHOT.jar \
org.example.PemjaVerification $TEST_HOME/bin/python $TEST_HOME/lib/python3.9/site-packages $TEST_HOME

# 测试结果

1. 直接运行报错

Python path configuration:
PYTHONHOME = (not set)
PYTHONPATH = ':/data/home/rockyxiong/workspace/DocParser'
program name = 'python3'
isolated = 0
environment = 1
user site = 1
import site = 1
sys._base_executable = '/usr/bin/python3'
sys.base_prefix = '/install'
sys.base_exec_prefix = '/install'
sys.platlibdir = 'lib'
sys.executable = '/usr/bin/python3'
sys.prefix = '/install'
sys.exec_prefix = '/install'
sys.path = [
'',
'/data/home/rockyxiong/workspace/DocParser',
'/install/lib/python39.zip',
'/install/lib/python3.9',
'/install/lib/lib-dynload',
]
Fatal Python error: init_fs_encoding: failed to get the Python codec of the filesystem encoding
Python runtime state: core initialized
ModuleNotFoundError: No module named 'encodings'

2. 先export PYTHON_HOME 运行正常

export PYTHONHOME="$TEST_HOME"
java -cp pemja-test-1.0-SNAPSHOT.jar \
org.example.PemjaVerification $TEST_HOME/bin/python $TEST_HOME/lib/python3.9/site-packages $TEST_HOME
