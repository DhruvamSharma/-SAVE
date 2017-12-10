package dell.kotlintrialapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dell.kotlintrialapp.databinding.ActivityTrialBinding

class TrialActivity : AppCompatActivity() {

    var a : String=""

    lateinit var binding: ActivityTrialBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //Toast.makeText(this,a,Toast.LENGTH_SHORT).show()
        //setContentView(R.layout.activity_trial)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_trial)
        var repository=Repository("Initial Repo","Dhruvam",false,0,5)
        binding.repository=repository
        binding.executePendingBindings()
    }
}
