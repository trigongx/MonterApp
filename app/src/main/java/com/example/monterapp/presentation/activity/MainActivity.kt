package com.example.monterapp.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monterapp.R
import com.example.monterapp.app.App
import com.example.monterapp.data.local.pref.Pref
import com.example.monterapp.data.models.Application
import com.example.monterapp.data.models.Monter
import com.example.monterapp.databinding.ActivityMainBinding
import com.example.monterapp.presentation.dashboard.ApplicationRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pref:Pref by lazy {
        Pref(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_profile,R.id.authFragment,R.id.detailsApplicationFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!pref.getUserLoggedInStatus()) {
            Log.d("ololo", "onCreate: ${pref.getUserLoggedInStatus()}")
            navController.popBackStack()
            navController.navigate(R.id.authFragment)
        }

        navController.addOnDestinationChangedListener{_,destination,_ ->
            when (destination.id){
                R.id.authFragment -> {
                    binding.navView.isVisible = false
                    supportActionBar?.hide()
                }
                R.id.detailsApplicationFragment -> {
                    binding.navView.isVisible = false
                }
                else -> {
                    navView.isVisible = true
                    supportActionBar?.show()
                }
            }
        }

        val newMonter1 = Monter(login = "Den", password = "3105", fullName = "Болотбеков Данияр", regionName = "Bishkek")
        val newMonter2 = Monter(login = "Uli", password = "2402", fullName = "Талантбеков Улубек", regionName = "Naryn")
        val newMonter3 = Monter(login = "Bekbol", password = "0108", fullName = "Камчыбеков Бекбол", regionName = "Osh")

        val newApplication1 = Application(address = "ж/м Калыс-Ордо ул.25 дом 4", regionName = "Bishkek", phoneNumber = 702260383, personalAccount = 1112, date = "12.01.2023", reason = "Поломка провода", status = false)
        val newApplication2 = Application(address = "ж/м Кок-Жар ул.20 дом 8", regionName = "Bishkek", phoneNumber = 703360383, personalAccount = 1113, date = "11.01.2023", reason = "Поломка провода", status = false)
        val newApplication3 = Application(address = "мкр.Улан-2 дом 130/9 кв.39", regionName = "Bishkek", phoneNumber = 704460383, personalAccount = 1114, date = "10.01.2023", reason = "Поломка провода", status = false)
        val newApplication4 = Application(address = "Ленина 76 дом 2", regionName = "Naryn", phoneNumber = 706826049, personalAccount = 2112, date = "01.01.2023", reason = "Поломка крышки", status = false)
        val newApplication5 = Application(address = "Ленина 106 дом 4", regionName = "Naryn", phoneNumber = 707828049, personalAccount = 2113, date = "12.01.2024", reason = "Поломка крышки", status = false)
        val newApplication6 = Application(address = "Ленина 52 дом 12", regionName = "Naryn", phoneNumber = 708828049, personalAccount = 2114, date = "07.01.2024", reason = "Поломка крышки", status = false)
        val newApplication7 = Application(address = "Салиева 25 дом 1", regionName = "Osh", phoneNumber = 700066555, personalAccount = 3112, date = "05.01.2024", reason = "Поломка выключателя", status = false)
        val newApplication8 = Application(address = "Салиева 27 дом 12", regionName = "Osh", phoneNumber = 700055666, personalAccount = 3113, date = "13.01.2023", reason = "Поломка выключателя", status = false)
        val newApplication9 = Application(address = "Салиева 25 дом 17", regionName = "Osh", phoneNumber = 700044555, personalAccount = 3114, date = "06.01.2023", reason = "Поломка выключателя", status = false)

        CoroutineScope(Dispatchers.IO).launch{
            setBaseData(
                newMonter1,
                newMonter2,
                newMonter3,
                newApplication1,
                newApplication2,
                newApplication3,
                newApplication4,
                newApplication5,
                newApplication6,
                newApplication7,
                newApplication8,
                newApplication9
            )
        }

    }

        private suspend fun setBaseData(
        newMonter1: Monter,
        newMonter2: Monter,
        newMonter3: Monter,
        newApplication1: Application,
        newApplication2: Application,
        newApplication3: Application,
        newApplication4: Application,
        newApplication5: Application,
        newApplication6: Application,
        newApplication7: Application,
        newApplication8: Application,
        newApplication9: Application
        ) {
            val isMontersNotEmpty = App.database.getMonterDao().isTableNotEmpty()
            if (!isMontersNotEmpty) {
                App.database.getMonterDao().insertMonter(newMonter1)
                App.database.getMonterDao().insertMonter(newMonter2)
                App.database.getMonterDao().insertMonter(newMonter3)
            }
            val isApplicationsNotEmpty = App.database.getApplicationDao().isTableNotEmpty()
            if (!isApplicationsNotEmpty) {
                App.database.getApplicationDao().insertApplication(newApplication1)
                App.database.getApplicationDao().insertApplication(newApplication2)
                App.database.getApplicationDao().insertApplication(newApplication3)
                App.database.getApplicationDao().insertApplication(newApplication4)
                App.database.getApplicationDao().insertApplication(newApplication5)
                App.database.getApplicationDao().insertApplication(newApplication6)
                App.database.getApplicationDao().insertApplication(newApplication7)
                App.database.getApplicationDao().insertApplication(newApplication8)
                App.database.getApplicationDao().insertApplication(newApplication9)
            }
    }


}