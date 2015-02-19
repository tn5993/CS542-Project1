# 
<p>Cory Hayward, Thinh Nguyen, He Xuebin</p>
<p>Project 1 for Database Management System class</p>
<p>Professor Singh</p>
<p>February 18, 2015</p>

<h4>Introduction</h4>
<p>Following intructions at http://cs542.wpi.datathinks.org/proj, this project aims to provide a better understanding for Isolation level of Database Management System (DBMS). The technologies that we employed was MySQL database and Java.
<h4> UML Diagram </h4>
<p> Here is the UML Diagram that we followed to implement our class </p>
<div style="text-align:center"><img src = "https://cloud.githubusercontent.com/assets/8074347/6260952/f61a0006-b7b4-11e4-93d5-ca956515f45c.png" width="60%"/></div>
<h4></h4>
<h4>Using MySQL to import large byte[] array</h4>
<p> We used Mysql with the following relation's declaration </p>
<code>CREATE TABLE store (
	k INT, 
	v LONGBLOB, 
	PRIMARY KEY (k)
);
</code>

<p> One of the major challenges that we encountered was to import a large amount of data into the database using byte[] array. To do this, we used LONGBLOB constraint for attribute v. Then, we set the following property for my.cnf file in our mysql's installation directory. Below are step by step instruction.<br />
<blockquote>
>>> cd /usr/local/mysql <br />
>>> chmod 777 my.cnf (for the file to be writable)<br />
>>> vi my.cnf <br />
</blockquote> <br />

<strong>Then add the following line</strong>
<blockquote>
innodb_log_file_size=1G   /* increase the size in bytes of each log file in a log group. */ <br />
innodb_log_buffer_size=64M /* size in bytes of the buffer that InnoDB uses to write to the log files on disk. */ <br />
max_allowed_packet=1073741824 /* set max allowed packet size */
</blockquote>
<strong>After, remember to change your permission back. Otherwise, the my.cnf setting will be ignore</strong><br/>
<code>chmod 644 my.cnf</code>
</p>

<h4> Result </h4>
<p>
We used threads as transactions to access the database at SERIALIZABLE isolation level. We saw that it blocked the whole table for one transaction to commit then served another transaction. However, there existed an interesting scenerio that we notice. Consider the following:
</p>
<blockquote>
Transaction 1 get big data at key<br/>
Transaction 2 update row at key<br/>
Transaction 3 get big data at key (scheduled milliseconds or seconds later)<br/>
</blockquote>

<p>
Instead of letting transaction 2 go first, transaction 3 seems to have privilege to access the table before transaction 2. But because the scenerio went as expected with small byte[] data, we believe the big data scenario is a special way that mysql handle updating and getting big data.
</p>

<h4> Conclusion </h4>
<p>We learned more about SERIALIZABLE isolation level in mysql as well as understanding more about mysql's underlying structure (configuration, log, isolation, constraint, etc...)</p>


