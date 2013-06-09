# -*- coding: utf-8 -*
#此脚本将从一个原始样本集中构造用于进行topN测试的训练集和测试集，生成top_n_training.dat和top_n_testing.dat
#第一个参数指定原始样本集,数据集的分隔符请在代码中指定
#训练集：把原始数据中，每个用户给予最高评分的物品去掉
#测试集：记录原始数据中去掉的（用户，物品，评分）
import mmap,sys
import contextlib

with open(sys.argv[1],'r') as fwhole:
    with open('top_n_training.dat','w') as ftrain:
        with open('top_n_testing.dat','w') as ftest:
            with contextlib.closing(mmap.mmap(fwhole.fileno(),0,access=mmap.ACCESS_READ)) as mwhole:
                #with contextlib.closing(mmap.mmap(ftest.fileno(),0,access=mmap.ACCESS_WRITE)) as mtest:
                #with contextlib.closing(mmap.mmap(ftrain.fileno(),0,access=mmap.ACCESS_WRITE)) as mtrain:
                currentUser=''#初始时刻当前用户为空
                currentRate=0 #当前最高评分
                currentLine='' #拥有当前最高评分的example
                while True:
                        line=mwhole.readline()
                        if not line:
                            ftest.write(currentLine)
                            break
                        ex=line.split('::') #原始样本的分隔符，根据具体情况改
                        exRate=int(ex[2])
                        if currentUser==ex[0]:
                            if currentRate<exRate:
                                ftrain.write(currentLine)
                                currentRate=exRate #当前最高评分小于正在处理的样本评分，就更新当前最高评分
                                currentLine=line  #更新当前最高评分的样本
                            else:
                                ftrain.write(line)
                        else:
                            ftest.write(currentLine)
                            currentLine=line
                            currentRate=exRate
                            currentUser=ex[0]
print 'finished'
                            
                            
                            
                            
                            
                    
                    
                    