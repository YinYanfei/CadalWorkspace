# -*- coding: utf-8 -*
#此脚本计算collabstream预测结果的topN命中率
#第一个参数指定collabstream训练出的user矩阵的文件，第二参数指定item矩阵的文件，第三个参数指定topN的测试集,第四个参数指定K的维数
#第五个参数topN的N
import mmap,sys,contextlib

with open(sys.argv[1],'r') as f_user:
    with open(sys.argv[2],'r') as f_item:
        with open(sys.argv[3],'r') as f_test:
            with contextlib.closing(mmap.mmap(f_user.fileno(),0,access=mmap.ACCESS_READ)) as m_user:
                with contextlib.closing(mmap.mmap(f_item.fileno(),0,access=mmap.ACCESS_READ)) as m_item:
                    with contextlib.closing(mmap.mmap(f_test.fileno(),0,access=mmap.ACCESS_READ)) as m_test:
                        total=0 #总用户数
                        hit=0  #命中的用户数
                        k=int(sys.argv[4]) #隐变量k的维数
                        n=int(sys.argv[5])
                        while True:
                            user_line=m_user.readline()
                            if not user_line:
                                break
                            user_k=user_line.split(' ') #用户的k维隐变量表示
                            m_item.seek(0)
                            user_rates=[] #用户对各个物品的评分预测
                            item_id=0
                            while True:
                                item_line=m_item.readline()
                                if not item_line:
                                    topdict=dict(user_rates)
                                    test_line=m_test.readline()
                                    if not test_line:
                                        break
                                    test_rate=test_line.split(' ')
                                    test_itemid=int(test_rate[1])
                                    if topdict.has_key(test_itemid):#检查topN中有没有test集中对应的item
                                        hit+=1 #命中，hit和total都加1
                                        total+=1
                                        break
                                    else :
                                        total+=1 #未命中，total加1
                                    break
                                item_id+=1
                                item_k=item_line.split(' ')
                                i=1 #输出文件第一列是行号,从0开始的
                                sum=0.0 #用户对某样物品的评分
                                while i <=k: #算预测评分
                                    sum=sum+float(user_k[i])*float(item_k[i])
                                    i+=1
                                user_rates.append((item_id,sum))
                                if len(user_rates)<=n :
                                    continue  #topN评分集未满
                                user_rates.sort(key=lambda x:x[1],reverse=True) #top评分集超过n个时，进行排序，取前n个
                                user_rates[n:]=[] #只保留topN个
print 'The hit rate is %f'%(hit/total)
                                
                                
                                    
                                    
                                    
                                
                                    
                                    
                                    
                                    
                                    
                                